package kr.hhplus.be.server.coupon.presentation.dto;


public record CouponIssuedRequestDTO(
        int quantity,
        long couponId
){
}
