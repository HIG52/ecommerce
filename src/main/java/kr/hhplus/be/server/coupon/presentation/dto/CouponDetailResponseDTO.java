package kr.hhplus.be.server.coupon.presentation.dto;

import kr.hhplus.be.server.common.type.CouponType;

import java.time.LocalDateTime;

public record CouponDetailResponseDTO(
        long couponId,
        long couponQuantity,
        String couponName,

        long userCouponId,
        boolean downloadResult,

        String message,

        CouponType couponType,
        long couponAmount,
        LocalDateTime expirationDate,
        long minUsageAmount,
        long maxDiscountAmount
){
}
