package kr.hhplus.be.server.coupon.domain.entity;

import kr.hhplus.be.server.common.type.CouponType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CouponTest {

    @Test
    @DisplayName("정상적으로 Coupon 객체를 생성한다.")
    void createCoupon() {
        // given
        String couponName = "10% 할인 쿠폰";
        CouponType couponType = CouponType.PERCENTAGE;
        Long couponAmount = 10L;
        Integer couponQuantity = 100;
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(7);
        Long minUsageAmount = 10000L;
        Long maxDiscountAmount = 5000L;

        // when
        Coupon coupon = Coupon.createCoupon(
                couponName,
                couponType,
                couponAmount,
                couponQuantity,
                expirationDate,
                minUsageAmount,
                maxDiscountAmount
        );

        // then
        assertThat(coupon.getCouponName()).isEqualTo(couponName);
        assertThat(coupon.getCouponType()).isEqualTo(couponType);
        assertThat(coupon.getCouponAmount()).isEqualTo(couponAmount);
        assertThat(coupon.getCouponQuantity()).isEqualTo(couponQuantity);
        assertThat(coupon.getExpirationDate()).isEqualTo(expirationDate);
        assertThat(coupon.getMinUsageAmount()).isEqualTo(minUsageAmount);
        assertThat(coupon.getMaxDiscountAmount()).isEqualTo(maxDiscountAmount);
    }

    @Test
    @DisplayName("쿠폰 수량을 감소시킨다.")
    void decreaseQuantity() {
        // given
        Coupon coupon = Coupon.createCoupon(
                "10% 할인 쿠폰",
                CouponType.PERCENTAGE,
                10L,
                5,
                LocalDateTime.now().plusDays(7),
                10000L,
                5000L
        );

        // when
        coupon.decreaseQuantity();

        // then
        assertThat(coupon.getCouponQuantity()).isEqualTo(4);
    }

}