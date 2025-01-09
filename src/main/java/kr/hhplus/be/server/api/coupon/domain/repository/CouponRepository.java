package kr.hhplus.be.server.api.coupon.domain.repository;

import kr.hhplus.be.server.api.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.api.coupon.domain.entity.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponRepository {
    Coupon getCoupon(long couponId);

    UserCoupon saveUserCoupon(UserCoupon userCoupon);

    UserCoupon getUserCoupon(long userCouponId);

    Page<Coupon> findAll(Pageable pageable);
}
