package kr.hhplus.be.server.redis;

public interface LockService {

    void lock(String lockKey);

    void unlock(String lockKey);

    default <T> T executeWithLock(String lockKey, LockCallback<T> callback) {
        lock(lockKey);
        try {
            return callback.doWithLock();
        } finally {
            unlock(lockKey);
        }
    }
}
