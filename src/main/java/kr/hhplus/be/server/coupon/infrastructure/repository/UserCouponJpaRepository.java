package kr.hhplus.be.server.coupon.infrastructure.repository;

import kr.hhplus.be.server.coupon.domain.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponJpaRepository extends JpaRepository<UserCoupon, Long> {

    UserCoupon findByUserIdAndCouponId(long userId, long couponId);

    UserCoupon findByUserCouponId(long userCouponId);
}
