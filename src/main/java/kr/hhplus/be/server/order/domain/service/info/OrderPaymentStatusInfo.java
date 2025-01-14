package kr.hhplus.be.server.order.domain.service.info;

import kr.hhplus.be.server.common.type.PaymentStatusType;

public record OrderPaymentStatusInfo(
        long orderId,
        PaymentStatusType paymentStatusType
) {
}
