package kr.hhplus.be.server.coupon.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.entity.AuditingFields;
import kr.hhplus.be.server.common.type.UserCouponType;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user_coupon")
public class UserCoupon extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id", nullable = false)
    private Long userCouponId;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private UserCouponType useYn;

    protected UserCoupon() {

    }

    private UserCoupon(Long couponId, Long userId, UserCouponType useYn) {
        this.couponId = couponId;
        this.userId = userId;
        this.useYn = useYn;
    }

    public static UserCoupon createUserCoupon(Long couponId, Long userId, UserCouponType useYn) {
        return new UserCoupon(couponId, userId, useYn);
    }

    public void updateUserCouponType(UserCouponType userCouponType) {
        this.useYn = userCouponType;
    }
}
