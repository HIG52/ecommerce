package kr.hhplus.be.server.api.coupon.presentation.dto;

public record CouponsResponseDTO(
        long couponId,
        String couponName,
        long couponQuantity

) {
}
