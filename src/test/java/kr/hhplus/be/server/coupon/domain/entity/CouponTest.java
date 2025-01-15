package kr.hhplus.be.server.coupon.domain.entity;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.common.type.CouponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTest {

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        coupon = Coupon.createCoupon(
                "Test Coupon",
                CouponType.PERCENTAGE,
                5000L,
                10,
                LocalDateTime.now().plusDays(10),
                10000L,
                2000L
        );
    }

    @Test
    @DisplayName("쿠폰 수량 감소 - 정상적으로 감소")
    void decreaseQuantity_Success() {
        // given
        int initialQuantity = coupon.getCouponQuantity();

        // when
        coupon.decreaseQuantity();

        // then
        assertThat(coupon.getCouponQuantity()).isEqualTo(initialQuantity - 1);
    }

    @Test
    @DisplayName("쿠폰 수량 감소 - 수량이 0미만 일때 예외 발생")
    void decreaseQuantity_OutOfStock() {
        // given
        coupon = Coupon.createCoupon(
                "Empty Coupon",
                CouponType.PERCENTAGE,
                5000L,
                -1,
                LocalDateTime.now().plusDays(10),
                10000L,
                2000L
        );

        // when & then
        assertThatThrownBy(coupon::decreaseQuantity)
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.COUPON_OUT_OF_STOCK.getMessage());
    }

    @Test
    @DisplayName("쿠폰 생성 - 모든 필드가 올바르게 초기화됨")
    void createCoupon_Success() {
        // then
        assertThat(coupon.getCouponName()).isEqualTo("Test Coupon");
        assertThat(coupon.getCouponType()).isEqualTo(CouponType.PERCENTAGE);
        assertThat(coupon.getCouponAmount()).isEqualTo(5000L);
        assertThat(coupon.getCouponQuantity()).isEqualTo(10);
        assertThat(coupon.getMinUsageAmount()).isEqualTo(10000L);
        assertThat(coupon.getMaxDiscountAmount()).isEqualTo(2000L);
        assertThat(coupon.getExpirationDate()).isAfter(LocalDateTime.now());
    }

}