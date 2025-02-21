package kr.hhplus.be.server.coupon.domain.repository;

import kr.hhplus.be.server.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.coupon.domain.entity.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponRepository {
    Coupon getCoupon(long couponId);

    UserCoupon saveUserCoupon(UserCoupon userCoupon);

    UserCoupon getUserCoupon(long userCouponId);

    Page<Coupon> findAll(Pageable pageable);

    Coupon getCouponWithLock(long couponId);

    Coupon saveCoupon(Coupon coupon);

    UserCoupon getMyUserCoupon(long userId, long couponId);

    int getUserCouponCount(long couponId);
}
