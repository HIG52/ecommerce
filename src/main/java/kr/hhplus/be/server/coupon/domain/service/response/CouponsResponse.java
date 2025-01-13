package kr.hhplus.be.server.coupon.domain.service.response;

import kr.hhplus.be.server.common.type.CouponType;

import java.time.LocalDateTime;

public record CouponsResponse(
        long couponId,
        String couponName,
        int couponQuantity
) {
}