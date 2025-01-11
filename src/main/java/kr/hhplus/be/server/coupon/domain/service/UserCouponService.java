package kr.hhplus.be.server.coupon.domain.service;

import kr.hhplus.be.server.coupon.domain.entity.UserCoupon;
import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.domain.service.request.CouponRequest;
import kr.hhplus.be.server.coupon.domain.service.response.UserCouponReponse;
import kr.hhplus.be.server.common.type.UserCouponType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public UserCouponReponse downloadUserCoupon(CouponRequest couponRequest) {

        UserCoupon myCoupon = couponRepository.getMyUserCoupon(couponRequest.userId(), couponRequest.couponId());

        if(myCoupon != null) {
            throw new IllegalArgumentException("이미 발급받은 쿠폰입니다.");
        }

        UserCoupon userCoupon = UserCoupon.createUserCoupon(couponRequest.userId(), couponRequest.couponId(), UserCouponType.N);

        UserCoupon resultUserCoupon = couponRepository.saveUserCoupon(userCoupon);

        return new UserCouponReponse(resultUserCoupon.getCouponId(), resultUserCoupon.getUserId(), resultUserCoupon.getUseYn());
    }

    @Transactional
    public UserCouponReponse updateUserCouponUseYn(long userCouponId, UserCouponType userCouponType) {
        UserCoupon userCoupon = couponRepository.getUserCoupon(userCouponId);

        if(userCoupon == null) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }

        userCoupon.updateUserCouponType(userCouponType);

        UserCoupon resultUserCoupon = couponRepository.saveUserCoupon(userCoupon);

        if(resultUserCoupon == null) {
            throw new IllegalArgumentException("쿠폰 사용처리에 실패하였습니다.");
        }

        return new UserCouponReponse(resultUserCoupon.getCouponId(), resultUserCoupon.getUserId(), resultUserCoupon.getUseYn());
    }

}
