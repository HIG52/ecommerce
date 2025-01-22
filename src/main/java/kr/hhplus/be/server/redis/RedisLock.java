package kr.hhplus.be.server.redis;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    /**
     * 단일 키
     * ex) @RedisLock(key = "#userId")
     */
    String key() default "";

    /**
     * 여러 개 키 (SpEL)
     * ex) @RedisLock(keys = "#{T(java.util.Arrays).asList('k1','k2')}")
     */
    String keys() default "";

}
