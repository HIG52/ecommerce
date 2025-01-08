package kr.hhplus.be.server.api.payment.domain.service.dto;

import kr.hhplus.be.server.common.type.PaymentStatusType;

public record PaymentResponse(
        long paymentId,
        long orderId,
        long paymentAmount,
        PaymentStatusType paymentStatus
) {
}
