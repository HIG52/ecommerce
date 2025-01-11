package kr.hhplus.be.server.order.domain.service.response;

import kr.hhplus.be.server.common.type.PaymentStatusType;

public record OrderPaymentStatusResponse(
        long orderId,
        PaymentStatusType paymentStatusType
) {
}
