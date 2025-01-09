package kr.hhplus.be.server.api.payment.presentation.dto;

public record PaymentRequestDTO(
        long userId,
        long orderId,
        long couponId,
        long paymentAmount
) {
}
