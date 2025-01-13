package kr.hhplus.be.server.payment.domain.service.request;

import kr.hhplus.be.server.common.type.PaymentStatusType;

public record PaymentCreateRequest(
        long orderId,
        long paymentAmount,
        long couponId,
        PaymentStatusType paymentStatus
) {
}
