package kr.hhplus.be.server.api.order.domain.service.response;

import kr.hhplus.be.server.common.type.PaymentStatusType;

public record OrderPaymentStatusResponse(
        long orderId,
        PaymentStatusType paymentStatusType
) {
}
