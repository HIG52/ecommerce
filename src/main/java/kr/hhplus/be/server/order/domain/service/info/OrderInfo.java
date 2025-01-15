package kr.hhplus.be.server.order.domain.service.info;

import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;

public record OrderInfo(
        long orderId,
        long userId,
        long orderTotalPrice,
        PaymentStatusType paymentStatus,
        OrderStatusType status
) {
}
