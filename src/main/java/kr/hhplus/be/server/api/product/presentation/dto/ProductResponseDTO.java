package kr.hhplus.be.server.api.product.presentation.dto;

public record ProductResponseDTO(
        String productName,
        long productPrice,
        int productQuantity
) {
}
