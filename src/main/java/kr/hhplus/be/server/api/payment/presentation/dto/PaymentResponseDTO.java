package kr.hhplus.be.server.api.payment.presentation.dto;

public record PaymentResponseDTO(
        long paymentId,
        boolean paymentResult
) {
}
