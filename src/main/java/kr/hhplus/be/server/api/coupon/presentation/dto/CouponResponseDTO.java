package kr.hhplus.be.server.api.coupon.presentation.dto;


import kr.hhplus.be.server.common.type.CouponType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CouponResponseDTO {
    private long couponId;
    private long couponQuantity;
    private String couponName;

    private long userCouponId;
    private boolean downloadResult;

    private String message;

    private CouponType couponType;
    private long couponAmount;
    private LocalDateTime expirationDate;
    private long minUsageAmount;
    private long maxDiscountAmount;

}
