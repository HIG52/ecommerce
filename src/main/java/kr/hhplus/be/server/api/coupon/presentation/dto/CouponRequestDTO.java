package kr.hhplus.be.server.api.coupon.presentation.dto;


public class CouponRequestDTO {
    private long userId;
    private long couponId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }
}
