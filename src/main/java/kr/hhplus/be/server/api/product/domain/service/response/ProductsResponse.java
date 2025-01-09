package kr.hhplus.be.server.api.product.domain.service.response;

public record ProductsResponse(
        long productId,
        String productName,
        long productPrice,
        int productQuantity

) {
}
