package kr.hhplus.be.server.coupon.domain.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
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
            throw new CustomExceptionHandler(ErrorCode.COUPON_ALREADY_DOWNLOAD);
        }

        UserCoupon userCoupon = UserCoupon.createUserCoupon(couponRequest.userId(), couponRequest.couponId(), UserCouponType.N);

        UserCoupon resultUserCoupon = couponRepository.saveUserCoupon(userCoupon);

        return new UserCouponReponse(resultUserCoupon.getCouponId(), resultUserCoupon.getUserId(), resultUserCoupon.getUseYn());
    }

    @Transactional
    public UserCouponReponse updateUserCouponUseYn(long userCouponId, UserCouponType userCouponType) {
        UserCoupon userCoupon = couponRepository.getUserCoupon(userCouponId);

        if(userCoupon == null) {
            throw new CustomExceptionHandler(ErrorCode.COUPON_NOT_FOUND);
        }

        userCoupon.updateUserCouponType(userCouponType);

        UserCoupon resultUserCoupon = couponRepository.saveUserCoupon(userCoupon);

        if(resultUserCoupon == null) {
            throw new CustomExceptionHandler(ErrorCode.COUPON_USE_FAILED);
        }

        return new UserCouponReponse(resultUserCoupon.getCouponId(), resultUserCoupon.getUserId(), resultUserCoupon.getUseYn());
    }

}
