package kr.hhplus.be.server.order.presentation.dto;

import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;

public record OrderResponseDTO(
        long orderId,
        long userId,
        long orderTotalPrice,
        PaymentStatusType paymentStatus,
        OrderStatusType status,
        long orderQuantity,
        long orderAmount
) {

}
