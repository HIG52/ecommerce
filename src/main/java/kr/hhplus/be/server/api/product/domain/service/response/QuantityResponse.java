package kr.hhplus.be.server.api.product.domain.service.response;

public record QuantityResponse(
        long productId,
        int productQuantity
) {
}
