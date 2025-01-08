package kr.hhplus.be.server.api.product.domain.service.dto;

public record QuantityResponse(
        long productId,
        int productQuantity
) {
}
