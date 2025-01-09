package kr.hhplus.be.server.api.order.domain.service.response;

import kr.hhplus.be.server.common.type.OrderStatusType;

public record OrderStatusResponse(
        long orderId,
        OrderStatusType orderStatus
) {
}
