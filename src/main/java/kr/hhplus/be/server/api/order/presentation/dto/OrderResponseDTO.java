package kr.hhplus.be.server.api.order.presentation.dto;

import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseDTO {

    private long orderId;
    private long userId;
    private long totalPrice;
    private PaymentStatusType paymentStatus;
    private OrderStatusType status;
    private long productId;
    private long orderQuantity;
    private long orderAmount;
    private String message;




}
