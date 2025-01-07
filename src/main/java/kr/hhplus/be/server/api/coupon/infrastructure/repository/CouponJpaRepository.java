package kr.hhplus.be.server.api.coupon.infrastructure.repository;

import kr.hhplus.be.server.api.coupon.domain.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
    Coupon findByCouponId(long couponId);
}
