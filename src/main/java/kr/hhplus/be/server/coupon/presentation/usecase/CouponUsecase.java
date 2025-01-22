package kr.hhplus.be.server.coupon.presentation.usecase;

import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.coupon.domain.service.CouponService;
import kr.hhplus.be.server.coupon.domain.service.UserCouponService;
import kr.hhplus.be.server.coupon.domain.service.request.CouponRequest;
import kr.hhplus.be.server.coupon.domain.service.info.CouponInfo;
import kr.hhplus.be.server.coupon.domain.service.info.UserCouponInfo;
import kr.hhplus.be.server.coupon.presentation.dto.CouponRequestDTO;
import kr.hhplus.be.server.coupon.presentation.dto.UserCouponResponseDTO;
import kr.hhplus.be.server.redis.RedisLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouponUsecase {

    private final BalanceService balanceService;
    private final CouponService couponService;
    private final UserCouponService userCouponService;


    @Transactional
    @RedisLock(key = "'couponId:' + #couponRequestDTO.couponId()")
    public UserCouponResponseDTO downloadUserCoupon(CouponRequestDTO couponRequestDTO) {

        balanceService.getUserBalance(couponRequestDTO.userId());

        CouponRequest couponRequest = new CouponRequest(
                couponRequestDTO.userId(),
                couponRequestDTO.couponId()
        );

        CouponInfo couponLockResponse = couponService.getCouponLock(couponRequest.couponId());

        UserCouponInfo userCouponInfo = userCouponService.downloadUserCoupon(couponRequest);

        return new UserCouponResponseDTO(
                userCouponInfo.userId(),
                userCouponInfo.couponId(),
                userCouponInfo.useYn()
        );
    }

}
