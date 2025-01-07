package kr.hhplus.be.server.api.payment.presentation.dto;

public class PaymentRequestDTO {
    private long userId;
    private long orderId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
