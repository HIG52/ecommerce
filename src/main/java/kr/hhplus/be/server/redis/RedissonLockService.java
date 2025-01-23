package kr.hhplus.be.server.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("redisson")
public class RedissonLockService implements LockService {

    private final RedissonClient redissonClient;

    private static final String LOCK_PREFIX = "redisson:";
    private static final long LOCK_WAIT_TIME_SECONDS = 20L; // 락 대기 시간
    private static final long LOCK_LEASE_TIME_SECONDS = 30L; // 락 유지 시간

    private String getKey(String lockKey) {
        return LOCK_PREFIX + lockKey;
    }

    @Override
    public void lock(String lockKey) {
        RLock lock = redissonClient.getLock(getKey(lockKey));
        try {
            log.info("Trying to acquire lock: {}", lockKey);
            boolean acquired = lock.tryLock(LOCK_WAIT_TIME_SECONDS, LOCK_LEASE_TIME_SECONDS, TimeUnit.SECONDS); // 락 대기 및 유지 시간 설정
            if (!acquired) {
                log.warn("Failed to acquire lock: {}", lockKey);
                throw new RuntimeException("Unable to acquire lock for key: " + lockKey);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while acquiring lock: " + lockKey, e);
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
            log.info("Lock released: {}", lockKey);
        } else {
            log.warn("Lock not held by current thread for key: {}", lockKey);
        }
    }

}
