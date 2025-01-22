package kr.hhplus.be.server.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("lettuce")
public class LettuceLockService implements LockService {

    private final StringRedisTemplate redisTemplate;

    // 현재 스레드가 보유한 lock value를 기억해두기 위한 ThreadLocal
    private final ThreadLocal<String> lockValueHolder = new ThreadLocal<>();

    private static final long LOCK_EXPIRE_SECONDS = 5; // 예시

    @Override
    public void lock(String lockKey) {
        String lockVal = UUID.randomUUID().toString();
        lockValueHolder.set(lockVal);

        while (true) {
            Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, lockVal, Duration.ofSeconds(LOCK_EXPIRE_SECONDS));
            if (Boolean.TRUE.equals(success)) {
                log.info("Lock acquired: key={}, value={}", lockKey, lockVal);
                break;
            }

            // 잠시 대기 후 재시도
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted during lock acquisition", e);
            }
        }
    }

    @Override
    public void unlock(String lockKey) {
        String lockVal = lockValueHolder.get();
        String currentVal = redisTemplate.opsForValue().get(lockKey);

        // 내가 가진 락 값과 동일할 때만 삭제
        if (lockVal != null && lockVal.equals(currentVal)) {
            redisTemplate.delete(lockKey);
            log.info("Lock released: key={}, value={}", lockKey, lockVal);
        } else {
            log.warn("Attempted to release a lock that is not owned: key={}, currentValue={}, threadValue={}", lockKey, currentVal, lockVal);
        }
    }

    @Override
    public <T> T executeWithLock(String lockKey, LockCallback<T> callback) {
        lock(lockKey);
        try {
            return callback.doWithLock();
        } finally {
            unlock(lockKey);
        }
    }

}
