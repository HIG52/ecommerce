package kr.hhplus.be.server.coupon.presentation.dto;

import kr.hhplus.be.server.common.type.UserCouponType;

public record UserCouponResponseDTO(
        long couponId,
        long userId,
        UserCouponType useYn
){
}
