package kr.hhplus.be.server.api.product.presentation.dto;

public class ProductResponseDTO {
    private long productId;
    private String productName;
    private long productPrice;
    private long ProductQuantity;
    private String message;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public long getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(long productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
