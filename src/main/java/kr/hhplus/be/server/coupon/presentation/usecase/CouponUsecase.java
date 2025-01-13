package kr.hhplus.be.server.coupon.presentation.usecase;

import kr.hhplus.be.server.coupon.domain.service.CouponService;
import kr.hhplus.be.server.coupon.domain.service.UserCouponService;
import kr.hhplus.be.server.coupon.domain.service.request.CouponRequest;
import kr.hhplus.be.server.coupon.domain.service.response.CouponResponse;
import kr.hhplus.be.server.coupon.domain.service.response.UserCouponReponse;
import kr.hhplus.be.server.coupon.presentation.dto.CouponRequestDTO;
import kr.hhplus.be.server.coupon.presentation.dto.UserCouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouponUsecase {

    private final CouponService couponService;
    private final UserCouponService userCouponService;

    @Transactional
    public UserCouponResponseDTO downloadUserCoupon(CouponRequestDTO couponRequestDTO) {

        CouponRequest couponRequest = new CouponRequest(
                couponRequestDTO.userId(),
                couponRequestDTO.couponId()
        );

        CouponResponse couponLockResponse = couponService.getCouponLock(couponRequest.couponId());

        UserCouponReponse userCouponReponse = userCouponService.downloadUserCoupon(couponRequest);

        return new UserCouponResponseDTO(
                userCouponReponse.userId(),
                userCouponReponse.couponId(),
                userCouponReponse.useYn()
        );
    }

}
