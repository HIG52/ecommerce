package kr.hhplus.be.server.api.coupon.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.entity.AuditingFields;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user_coupon")
public class UserCoupon extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon", nullable = false)
    private Long id;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "user_id")
    private Long userId;

    protected UserCoupon() {

    }

    private UserCoupon(Long couponId, Long userId) {
        this.couponId = couponId;
        this.userId = userId;
    }

    public static UserCoupon createUserCoupon(Long couponId, Long userId) {
        return new UserCoupon(couponId, userId);
    }

}
