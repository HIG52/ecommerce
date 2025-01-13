package kr.hhplus.be.server.coupon.presentation.dto;

import kr.hhplus.be.server.common.type.CouponType;

import java.time.LocalDateTime;

public record CouponResponseDTO(
        long couponId,
        String couponName,
        long couponAmount,
        CouponType couponType,
        long couponQuantity,
        LocalDateTime expirationDate,
        long maxDiscountAmount,
        long minUsageAmount
) {
}
