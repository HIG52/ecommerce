package kr.hhplus.be.server.api.dto;

import java.util.List;

public class OrderRequestDTO {
    private Long userId; // 유저 아이디
    private Long orderId; // 주문 아이디
    private List<Long> productIds; // 상품 아이디 목록
    private List<Long> productQuantities; // 상품 개수 목록
    private List<Long> productPrices; // 상품 가격 목록

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(List<Long> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public List<Long> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<Long> productPrices) {
        this.productPrices = productPrices;
    }
}
