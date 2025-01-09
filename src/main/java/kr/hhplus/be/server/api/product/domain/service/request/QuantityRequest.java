package kr.hhplus.be.server.api.product.domain.service.request;

public record QuantityRequest(
        long productId,
        int productQuantity
) {

}
