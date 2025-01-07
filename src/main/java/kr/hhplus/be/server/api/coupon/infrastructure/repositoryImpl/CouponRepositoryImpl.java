package kr.hhplus.be.server.api.coupon.infrastructure.repositoryImpl;

import kr.hhplus.be.server.api.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.api.coupon.domain.entity.UserCoupon;
import kr.hhplus.be.server.api.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.api.coupon.infrastructure.repository.CouponJpaRepository;
import kr.hhplus.be.server.api.coupon.infrastructure.repository.UserCouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {
    private final CouponJpaRepository couponJpaRepository;
    private final UserCouponJpaRepository userCouponJpaRepository;

    @Override
    public Coupon getCoupon(long couponId) {
        return couponJpaRepository.findByCouponId(couponId);
    }

    @Override
    public void saveUserCoupon(UserCoupon userCoupon) {
        userCouponJpaRepository.save(userCoupon);
    }

}