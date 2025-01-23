package kr.hhplus.be.server.redis;

@FunctionalInterface
public interface LockCallback<T> {
    T doWithLock();
}
