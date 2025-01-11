package kr.hhplus.be.server.product.presentation.dto;

public record ProductResponseDTO(
        String productName,
        long productPrice,
        int productQuantity
) {
}
