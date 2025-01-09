package kr.hhplus.be.server.api.order.domain.service.response;

import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;

public record OrderResponse(
        long userId,
        long totalPrice,
        PaymentStatusType paymentStatus,
        OrderStatusType status
) {
}
