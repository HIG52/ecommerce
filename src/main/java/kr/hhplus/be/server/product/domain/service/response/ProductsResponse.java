package kr.hhplus.be.server.product.domain.service.response;

public record ProductsResponse(
        long productId,
        String productName,
        long productPrice,
        int productQuantity

) {
}
