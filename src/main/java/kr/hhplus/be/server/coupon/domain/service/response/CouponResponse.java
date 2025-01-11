package kr.hhplus.be.server.coupon.domain.service.response;

import kr.hhplus.be.server.common.type.CouponType;

import java.time.LocalDateTime;

public record CouponResponse(
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
