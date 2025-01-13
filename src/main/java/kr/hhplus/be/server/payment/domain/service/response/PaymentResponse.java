package kr.hhplus.be.server.payment.domain.service.response;

import kr.hhplus.be.server.common.type.PaymentStatusType;

public record PaymentResponse(
        long paymentId,
        long orderId,
        long couponId,
        long paymentAmount,
        PaymentStatusType paymentStatus
) {
}
