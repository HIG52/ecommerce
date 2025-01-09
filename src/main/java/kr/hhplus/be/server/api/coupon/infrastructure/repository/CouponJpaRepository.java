package kr.hhplus.be.server.api.coupon.infrastructure.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.api.coupon.domain.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
    Coupon findByCouponId(long couponId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.couponId = :couponId")
    Coupon findByCouponIdWithLock(@Param("couponId") long couponId);
}
