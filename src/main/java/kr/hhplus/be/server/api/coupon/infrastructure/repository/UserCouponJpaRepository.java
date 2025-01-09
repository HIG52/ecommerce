package kr.hhplus.be.server.api.coupon.infrastructure.repository;

import kr.hhplus.be.server.api.coupon.domain.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponJpaRepository extends JpaRepository<UserCoupon, Long> {

    UserCoupon findByUserCouponId(long userCouponId);
}
