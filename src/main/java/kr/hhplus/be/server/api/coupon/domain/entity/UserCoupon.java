package kr.hhplus.be.server.api.coupon.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.AuditingFields;

@Entity
@Table(name = "user_coupon")
public class UserCoupon extends AuditingFields {

    @Id
    @Column(name = "user_coupon", nullable = false)
    private Long id;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "user_id")
    private Long userId;
}
