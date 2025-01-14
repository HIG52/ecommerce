package kr.hhplus.be.server.coupon.domain.service.info;

import kr.hhplus.be.server.common.type.CouponType;

import java.time.LocalDateTime;

public record CouponInfo(
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
