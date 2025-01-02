package kr.hhplus.be.server.api.dto;

public class PaymentResponseDTO {
    private long paymentId;
    private boolean paymentResult;
    private String message;

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public boolean isPaymentResult() {
        return paymentResult;
    }

    public void setPaymentResult(boolean paymentResult) {
        this.paymentResult = paymentResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
