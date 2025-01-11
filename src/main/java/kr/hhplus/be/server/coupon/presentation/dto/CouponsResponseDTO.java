package kr.hhplus.be.server.coupon.presentation.dto;

public record CouponsResponseDTO(
        long couponId,
        String couponName,
        long couponQuantity

) {
}
