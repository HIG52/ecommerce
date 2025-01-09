package kr.hhplus.be.server.api.product.domain.service.response;

public record ProductResponse(
        String productName,
        long productPrice,
        int productQuantity
) {
}
