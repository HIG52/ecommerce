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
        List<String> lockKeys = resolveKeys(pjp, redisLock.keys());
        if (lockKeys.isEmpty()) {
            String singleKey = resolveKey(pjp, redisLock.key());
            if (singleKey != null) lockKeys = List.of(singleKey);
        }

        if (lockKeys.isEmpty()) throw new RuntimeException("No lock key specified.");

        List<String> acquiredKeys = new ArrayList<>();
        try {
            for (String key : lockKeys) {
                lockService.lock(key); // 락 획득
                acquiredKeys.add(key);
            }

            // 트랜잭션 종료 후 락 해제
            if (TransactionSynchronizationManager.isSynchronizationActive()) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCompletion(int status) {
                        for (String key : acquiredKeys) {
                            lockService.unlock(key); // 트랜잭션 종료 후 락 해제
                        }
                    }
                });
            }

            // 비즈니스 로직 실행
            return pjp.proceed();

        } catch (Throwable t) {
            log.error("Error during RedisLock acquisition: keys={}, error={}", acquiredKeys, t.getMessage());
            throw t;
        } finally {
            if (!TransactionSynchronizationManager.isSynchronizationActive() || acquiredKeys.isEmpty()) {
                for (String key : acquiredKeys) {
                    lockService.unlock(key);
                }
            }
        }
    }


    private String resolveKey(ProceedingJoinPoint joinPoint, String spel) {
        if (spel == null || spel.isBlank()) return null;
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = createContext(joinPoint);
        return parser.parseExpression(spel).getValue(context, String.class);
    }

    private List<String> resolveKeys(ProceedingJoinPoint joinPoint, String spel) {
        if (spel == null || spel.isBlank()) return List.of();
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = createContext(joinPoint);

        Object value = parser.parseExpression(spel).getValue(context);
        if (value instanceof List) return ((List<?>) value).stream().map(Object::toString).toList();
        if (value instanceof String[]) return Arrays.asList((String[]) value);
        if (value instanceof String) return List.of((String) value);
        return List.of();
    }

    private StandardEvaluationContext createContext(ProceedingJoinPoint joinPoint) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        IntStream.range(0, args.length).forEach(i -> context.setVariable(paramNames[i], args[i]));
        return context;
    }

}
