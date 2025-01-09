package kr.hhplus.be.server.api.product.presentation.dto;

public record ProductsResponseDTO(
        long productId,
        String productName,
        long productPrice,
        int productQuantity
) {
}
