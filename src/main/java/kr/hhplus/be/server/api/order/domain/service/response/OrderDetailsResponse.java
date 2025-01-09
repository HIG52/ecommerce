package kr.hhplus.be.server.api.order.domain.service.response;

public record OrderDetailsResponse(
        long orderId,
        long productId,
        int orderQuantity,
        long orderAmount
) {
}
