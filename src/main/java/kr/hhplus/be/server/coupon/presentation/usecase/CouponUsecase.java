package kr.hhplus.be.server.coupon.presentation.usecase;

import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.coupon.domain.service.CouponInventoryService;
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

    private final CouponService couponService;
    private final UserCouponService userCouponService;
    private final CouponInventoryService couponInventoryService;

    @Transactional
    public UserCouponResponseDTO downloadUserCoupon(CouponRequestDTO couponRequestDTO) {

        CouponRequest couponRequest = new CouponRequest(
                couponRequestDTO.userId(),
                couponRequestDTO.couponId()
        );

        //CouponInfo couponLockResponse = couponService.getCouponLock(couponRequest.couponId());

        // Redis를 사용하여 쿠폰 재고를 원자적으로 감소시키고, 수령자 기록을 남김 TODO: 정말 다중환경에서 잘 작동되는지
        boolean redisResult = couponInventoryService.processCouponDownload(couponRequest.couponId(), couponRequest.userId());

        // Redis에서 재고 감소가 실패하면 (재고 부족) 예외 발생
        if (!redisResult) {
            throw new CustomExceptionHandler(ErrorCode.COUPON_OUT_OF_STOCK);
        }

        UserCouponInfo userCouponInfo = userCouponService.downloadUserCoupon(couponRequest);

        return new UserCouponResponseDTO(
                userCouponInfo.userId(),
                userCouponInfo.couponId(),
                userCouponInfo.useYn()
        );
    }

}
