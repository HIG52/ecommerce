package kr.hhplus.be.server.api.coupon.domain.repository;

import kr.hhplus.be.server.api.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.api.coupon.domain.entity.UserCoupon;

public interface CouponRepository {
    Coupon getCoupon(long couponId);

    void saveUserCoupon(UserCoupon userCoupon);
}
