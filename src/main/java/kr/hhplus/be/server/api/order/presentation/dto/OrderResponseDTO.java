package kr.hhplus.be.server.api.order.presentation.dto;

import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;

public record OrderResponseDTO(
        long orderId,
        long userId,
        long totalPrice,
        PaymentStatusType paymentStatus,
        OrderStatusType status,
        long productId,
        long orderQuantity,
        long orderAmount,
        String message
) {

}
