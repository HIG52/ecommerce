package kr.hhplus.be.server.product.domain.service.request;

public record QuantityRequest(
        long productId,
        int productQuantity
) {

}
