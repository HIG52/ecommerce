package kr.hhplus.be.server.coupon.infrastructure.repositoryImpl;

import kr.hhplus.be.server.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.coupon.domain.entity.UserCoupon;
import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.infrastructure.repository.CouponJpaRepository;
import kr.hhplus.be.server.coupon.infrastructure.repository.UserCouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public UserCoupon saveUserCoupon(UserCoupon userCoupon) {
        return userCouponJpaRepository.save(userCoupon);
    }

    @Override
    public UserCoupon getUserCoupon(long userCouponId) {
        return userCouponJpaRepository.findByUserCouponId(userCouponId);
    }

    @Override
    public Page<Coupon> findAll(Pageable pageable) {
        return couponJpaRepository.findAll(pageable);
    }

    @Override
    public Coupon getCouponWithLock(long couponId) {
        return couponJpaRepository.findByCouponIdWithLock(couponId);
    }

    @Override
    public Coupon saveCoupon(Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    public UserCoupon getMyUserCoupon(long userId, long couponId) {
        return userCouponJpaRepository.findByUserIdAndCouponId(userId, couponId);
    }

}
