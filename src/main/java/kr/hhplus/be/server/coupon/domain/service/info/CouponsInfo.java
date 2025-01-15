package kr.hhplus.be.server.coupon.domain.service.info;

public record CouponsInfo(
        long couponId,
        String couponName,
        int couponQuantity
) {
}
