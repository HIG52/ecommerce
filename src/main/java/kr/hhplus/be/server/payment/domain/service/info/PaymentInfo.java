package kr.hhplus.be.server.payment.domain.service.info;

import kr.hhplus.be.server.common.type.PaymentStatusType;

public record PaymentInfo(
        long paymentId,
        long orderId,
        long couponId,
        long paymentAmount,
        PaymentStatusType paymentStatus
) {
}
