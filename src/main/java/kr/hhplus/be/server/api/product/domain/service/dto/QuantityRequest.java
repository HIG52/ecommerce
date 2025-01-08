package kr.hhplus.be.server.api.product.domain.service.dto;

public record QuantityRequest(
        long productId,
        int productQuantity
) {

}
