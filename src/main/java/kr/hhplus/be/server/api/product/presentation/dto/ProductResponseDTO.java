package kr.hhplus.be.server.api.product.presentation.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private long productId;
    private String productName;
    private long productPrice;
    private int ProductQuantity;
    private String message;
}
