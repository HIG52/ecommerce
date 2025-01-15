package kr.hhplus.be.server.order.domain.service.info;

import kr.hhplus.be.server.common.type.OrderStatusType;

public record OrderStatusInfo(
        long orderId,
        OrderStatusType orderStatus
) {
}
