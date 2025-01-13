package kr.hhplus.be.server.order.domain.service.response;

public record OrderDetailsResponse(
        long orderId,
        long productId,
        int orderQuantity,
        long orderAmount
) {
}
