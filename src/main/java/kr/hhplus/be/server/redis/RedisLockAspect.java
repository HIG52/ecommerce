package kr.hhplus.be.server.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnBean(LockService.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
@Slf4j
public class RedisLockAspect {

    private final LockService lockService;

    @Around("@annotation(redisLock)")
    public Object doRedisLock(ProceedingJoinPoint pjp, RedisLock redisLock) throws Throwable {

        // 1) SpEL을 통해 keys / key 파싱 (복수 키 우선 사용)
        List<String> lockKeys = resolveKeys(pjp, redisLock.keys());
        if (lockKeys.isEmpty()) {
            // 만약 keys()가 비어있다면 단일 key()를 파싱
            String singleKey = resolveKey(pjp, redisLock.key());
            if (singleKey != null && !singleKey.isBlank()) {
                lockKeys = List.of(singleKey);
            }
        }

        if (lockKeys.isEmpty()) {
            // 키가 전혀 없다면 에러 처리
            throw new RuntimeException("No lock key specified.");
        }

        // 실제 획득한 락 목록 (중간 실패 시를 대비하여 기록)
        List<String> acquiredKeys = new ArrayList<>();

        // 2) 여러 키 각각에 대해 lock 획득
        try {
            for (String key : lockKeys) {
                lockService.lock(key);
                acquiredKeys.add(key);
                log.info("[RedisLockAspect] lock acquired: key={}", key);
            }
        } catch (Exception e) {
            // 중간에 하나라도 실패 시, 이미 획득한 키들은 해제
            log.error("Failed to acquire lock for some key, releasing acquired locks: {}", acquiredKeys, e);
            releaseLocks(acquiredKeys);
            throw e; // 원본 예외 다시 던짐
        }

        try {
            // 3) 트랜잭션이 활성화된 경우, Commit or Rollback 완료 후에 unlock
            if (TransactionSynchronizationManager.isSynchronizationActive()) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCompletion(int status) {
                        log.info("[RedisLockAspect] transaction completed, releasing all locks: {}", acquiredKeys);
                        releaseLocks(acquiredKeys);
                    }
                });
            }
            // 트랜잭션이 없다면 proceed 후 finally 블록에서 해제

            // 4) 실제 비즈니스 메서드 실행
            return pjp.proceed();

        } catch (Throwable t) {
            // 비즈니스 메서드에서 발생한 예외 → 롤백
            throw t;
        } finally {
            // 트랜잭션이 없는 경우에는 직접 unlock
            if (!TransactionSynchronizationManager.isSynchronizationActive()) {
                log.info("[RedisLockAspect] no transaction, releasing all locks: {}", acquiredKeys);
                releaseLocks(acquiredKeys);
            }
        }
    }

    /**
     * 단일 키용 SpEL 파싱
     */
    private String resolveKey(ProceedingJoinPoint joinPoint, String spel) {
        if (spel == null || spel.isBlank()) {
            return null;
        }
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = createContext(joinPoint);
        return parser.parseExpression(spel).getValue(context, String.class);
    }

    /**
     * 다중 키용 SpEL 파싱
     */
    @SuppressWarnings("unchecked")
    private List<String> resolveKeys(ProceedingJoinPoint joinPoint, String spel) {
        if (spel == null || spel.isBlank()) {
            return List.of();
        }
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = createContext(joinPoint);

        Object value = parser.parseExpression(spel).getValue(context);
        if (value == null) {
            return List.of();
        } else if (value instanceof List) {
            return ((List<?>) value).stream().map(Object::toString).toList();
        } else if (value instanceof String[]) {
            return Arrays.asList((String[]) value);
        } else if (value instanceof String) {
            return List.of((String) value);
        }
        // 기타 타입은 일단 빈 리스트
        return List.of();
    }

    /**
     * AOP 메서드 파라미터를 SpEL context에 주입
     */
    private StandardEvaluationContext createContext(ProceedingJoinPoint joinPoint) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();

        IntStream.range(0, args.length).forEach(i -> {
            context.setVariable(paramNames[i], args[i]);
        });
        return context;
    }

    /**
     * 획득한 락 해제
     */
    private void releaseLocks(List<String> keys) {
        for (String key : keys) {
            try {
                lockService.unlock(key);
                log.info("[RedisLockAspect] lock released: key={}", key);
            } catch (Exception e) {
                log.error("Failed to unlock key={}", key, e);
            }
        }
    }

}
