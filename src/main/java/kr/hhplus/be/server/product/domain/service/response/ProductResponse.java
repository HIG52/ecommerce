package kr.hhplus.be.server.product.domain.service.response;

public record ProductResponse(
        String productName,
        long productPrice,
        int productQuantity
) {
}
