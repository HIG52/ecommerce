package kr.hhplus.be.server.order.domain.service.response;

import kr.hhplus.be.server.common.type.OrderStatusType;

public record OrderStatusResponse(
        long orderId,
        OrderStatusType orderStatus
) {
}
