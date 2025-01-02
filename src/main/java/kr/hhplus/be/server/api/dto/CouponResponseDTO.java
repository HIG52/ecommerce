package kr.hhplus.be.server.api.dto;


import java.time.LocalDateTime;

public class CouponResponseDTO {
    private long couponId;
    private long couponQuantity;
    private String couponName;

    private long userCouponId;
    private boolean downloadResult;

    private String message;

    private String couponType;
    private int couponAmount;
    private LocalDateTime expirationDate;
    private int minUsageAmount;
    private int maxDiscountAmount;

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public long getCouponQuantity() {
        return couponQuantity;
    }

    public void setCouponQuantity(long couponQuantity) {
        this.couponQuantity = couponQuantity;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public long getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(long userCouponId) {
        this.userCouponId = userCouponId;
    }

    public boolean isDownloadResult() {
        return downloadResult;
    }

    public void setDownloadResult(boolean downloadResult) {
        this.downloadResult = downloadResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getMinUsageAmount() {
        return minUsageAmount;
    }

    public void setMinUsageAmount(int minUsageAmount) {
        this.minUsageAmount = minUsageAmount;
    }

    public int getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }
}
