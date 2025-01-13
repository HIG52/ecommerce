package kr.hhplus.be.server.coupon.domain.entity;

import kr.hhplus.be.server.common.type.UserCouponType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserCouponTest {

    @Test
    @DisplayName("정상적으로 UserCoupon 객체를 생성한다.")
    void createUserCoupon() {
        // given
        Long couponId = 1L;
        Long userId = 2L;
        UserCouponType useYn = UserCouponType.N;

        // when
        UserCoupon userCoupon = UserCoupon.createUserCoupon(couponId, userId, useYn);

        // then
        assertThat(userCoupon.getCouponId()).isEqualTo(couponId);
        assertThat(userCoupon.getUserId()).isEqualTo(userId);
        assertThat(userCoupon.getUseYn()).isEqualTo(useYn);
    }

    @Test
    @DisplayName("UserCouponType을 업데이트한다.")
    void updateUserCouponType() {
        // given
        UserCoupon userCoupon = UserCoupon.createUserCoupon(1L, 2L, UserCouponType.N);

        // when
        userCoupon.updateUserCouponType(UserCouponType.Y);

        // then
        assertThat(userCoupon.getUseYn()).isEqualTo(UserCouponType.Y);
    }

}