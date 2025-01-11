package kr.hhplus.be.server.order.domain.service.response;

import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;

public record OrderResponse(
        long orderId,
        long userId,
        long orderTotalPrice,
        PaymentStatusType paymentStatus,
        OrderStatusType status
) {
}
