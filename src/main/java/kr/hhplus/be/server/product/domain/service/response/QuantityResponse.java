package kr.hhplus.be.server.product.domain.service.response;

public record QuantityResponse(
        long productId,
        int productQuantity
) {
}
