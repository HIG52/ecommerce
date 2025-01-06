package kr.hhplus.be.server.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
public class Coupon extends AuditingFields{

    @Id
    @Column(name = "coupon_id", nullable = false)
    private Long id;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "coupon_type")
    private String couponType;

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


}
