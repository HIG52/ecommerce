package kr.hhplus.be.server.api.coupon.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.entity.AuditingFields;
import kr.hhplus.be.server.common.type.CouponType;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "coupon")
public class Coupon extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "coupon_name")
    private String couponName;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type")
    private CouponType couponType;

    @Column(name = "coupon_amount")
    private Long couponAmount;

    @Column(name = "coupon_quantity")
    private Integer couponQuantity;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "min_usage_amount")
    private Long minUsageAmount;

    @Column(name = "max_discount_amount")
    private Long maxDiscountAmount;

    protected Coupon() {

    }

    private Coupon(String couponName, CouponType couponType, Long couponAmount, Integer couponQuantity, LocalDateTime expirationDate, Long minUsageAmount, Long maxDiscountAmount) {
        this.couponName = couponName;
        this.couponType = couponType;
        this.couponAmount = couponAmount;
        this.couponQuantity = couponQuantity;
        this.expirationDate = expirationDate;
        this.minUsageAmount = minUsageAmount;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public static Coupon createCoupon(String couponName, CouponType couponType, Long couponAmount, Integer couponQuantity, LocalDateTime expirationDate, Long minUsageAmount, Long maxDiscountAmount) {
        return new Coupon(couponName, couponType, couponAmount, couponQuantity, expirationDate, minUsageAmount, maxDiscountAmount);
    }


    public void decreaseQuantity() {
        this.couponQuantity--;
    }
}
