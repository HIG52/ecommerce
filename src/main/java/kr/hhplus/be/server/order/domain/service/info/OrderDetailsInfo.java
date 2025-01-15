package kr.hhplus.be.server.order.domain.service.info;

public record OrderDetailsInfo(
        long orderId,
        long productId,
        int orderQuantity,
        long orderAmount
) {
}
