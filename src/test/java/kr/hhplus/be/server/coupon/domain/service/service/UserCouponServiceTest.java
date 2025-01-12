package kr.hhplus.be.server.coupon.domain.service.service;

import kr.hhplus.be.server.coupon.domain.entity.UserCoupon;
import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.domain.service.UserCouponService;
import kr.hhplus.be.server.coupon.domain.service.request.CouponRequest;
import kr.hhplus.be.server.coupon.domain.service.response.UserCouponReponse;
import kr.hhplus.be.server.common.type.UserCouponType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserCouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Test
    void 유저가_쿠폰을_다운로드하면_UserCouponResponse를_반환한다() {
        // given
        UserCouponService couponService = new UserCouponService(couponRepository);
        long userId = 1L;
        long couponId = 100L;

        UserCoupon savedUserCoupon = UserCoupon.createUserCoupon(couponId, userId, UserCouponType.N);

        // Mock 설정
        given(couponRepository.saveUserCoupon(any(UserCoupon.class))).willReturn(savedUserCoupon);
        CouponRequest couponRequest = new CouponRequest(userId, couponId);
        // when
        UserCouponReponse response = couponService.downloadUserCoupon(couponRequest);

        // then
        assertEquals(response.userId(), userId);
        assertEquals(response.couponId(), couponId);

        // saveUserCoupon 메서드 호출 검증
        verify(couponRepository).saveUserCoupon(any(UserCoupon.class));
    }

    @Test
    void userCouponId와_UseYn을_입력하면_쿠폰상태값변경후_UserCouponResponse를_반환한다() {
        // given
        UserCouponService userCouponService = new UserCouponService(couponRepository);
        long userCouponId = 1L;
        UserCouponType newType = UserCouponType.Y;

        UserCoupon userCoupon = UserCoupon.createUserCoupon(1L, 1L, UserCouponType.N);
        ReflectionTestUtils.setField(userCoupon, "userCouponId", userCouponId);

        UserCoupon updatedUserCoupon = UserCoupon.createUserCoupon(1L, 1L, newType);
        ReflectionTestUtils.setField(updatedUserCoupon, "userCouponId", userCouponId);

        given(couponRepository.getUserCoupon(userCouponId)).willReturn(userCoupon);
        given(couponRepository.saveUserCoupon(userCoupon)).willReturn(updatedUserCoupon);

        // when
        UserCouponReponse response = userCouponService.updateUserCouponUseYn(userCouponId, newType);

        // then
        assertThat(response.couponId()).isEqualTo(1L);
        assertThat(response.userId()).isEqualTo(1L);

        // verify repository methods
        verify(couponRepository).getUserCoupon(userCouponId);
        verify(couponRepository).saveUserCoupon(userCoupon);
    }

    @Test
    void 존재하지_않는_userCouponId로_갱신을_시도하면_IllegalArgumentException발생() {
        // given
        UserCouponService userCouponService = new UserCouponService(couponRepository);
        long userCouponId = 1L;
        UserCouponType newType = UserCouponType.Y;

        given(couponRepository.getUserCoupon(userCouponId)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> userCouponService.updateUserCouponUseYn(userCouponId, newType))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 쿠폰입니다.");

        // verify repository method
        verify(couponRepository).getUserCoupon(userCouponId);
        verify(couponRepository, never()).saveUserCoupon(any());
    }

    @Test
    void userCoupon_저장_실패시_IllegalArgumentException발생() {
        // given
        UserCouponService userCouponService = new UserCouponService(couponRepository);
        long userCouponId = 1L;
        UserCouponType newType = UserCouponType.Y;

        UserCoupon userCoupon = UserCoupon.createUserCoupon(1L, 1L, UserCouponType.N);
        ReflectionTestUtils.setField(userCoupon, "userCouponId", userCouponId);

        given(couponRepository.getUserCoupon(userCouponId)).willReturn(userCoupon);
        given(couponRepository.saveUserCoupon(userCoupon)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> userCouponService.updateUserCouponUseYn(userCouponId, newType))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 사용처리에 실패하였습니다.");

        // verify repository methods
        verify(couponRepository).getUserCoupon(userCouponId);
        verify(couponRepository).saveUserCoupon(userCoupon);
    }

}