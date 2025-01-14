package kr.hhplus.be.server.coupon.domain.service.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.coupon.domain.entity.UserCoupon;
import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.domain.service.CouponService;
import kr.hhplus.be.server.coupon.domain.service.UserCouponService;
import kr.hhplus.be.server.coupon.domain.service.request.CouponRequest;
import kr.hhplus.be.server.coupon.domain.service.response.CouponResponse;
import kr.hhplus.be.server.coupon.domain.service.response.CouponsResponse;
import kr.hhplus.be.server.coupon.domain.service.response.UserCouponReponse;
import kr.hhplus.be.server.common.type.CouponType;
import kr.hhplus.be.server.common.type.UserCouponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private UserCouponService userCouponService;

    private UserCoupon userCoupon;

    @BeforeEach
    void setUp() {
        userCoupon = UserCoupon.createUserCoupon(1L, 1L, UserCouponType.N);
        ReflectionTestUtils.setField(userCoupon, "userCouponId", 1L);
    }

    @Test
    @DisplayName("쿠폰 요청 정보를 입력하면 사용자 쿠폰이 다운로드된다")
    void downloadUserCoupon_Success() {
        // given
        CouponRequest couponRequest = new CouponRequest(1L, 1L);
        given(couponRepository.getMyUserCoupon(1L, 1L)).willReturn(null);
        given(couponRepository.saveUserCoupon(any(UserCoupon.class))).willReturn(userCoupon);

        // when
        UserCouponReponse response = userCouponService.downloadUserCoupon(couponRequest);

        // then
        assertThat(response.couponId()).isEqualTo(1L);
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.useYn()).isEqualTo(UserCouponType.N);

        verify(couponRepository).getMyUserCoupon(1L, 1L);
        verify(couponRepository).saveUserCoupon(any(UserCoupon.class));
    }

    @Test
    @DisplayName("이미 다운로드된 쿠폰을 요청하면 CustomExceptionHandler를 반환한다")
    void downloadUserCoupon_AlreadyDownloaded() {
        // given
        CouponRequest couponRequest = new CouponRequest(1L, 1L);
        given(couponRepository.getMyUserCoupon(1L, 1L)).willReturn(userCoupon);

        // when & then
        assertThatThrownBy(() -> userCouponService.downloadUserCoupon(couponRequest))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.COUPON_ALREADY_DOWNLOAD.getMessage());
    }

    @Test
    @DisplayName("사용자 쿠폰 ID와 상태를 입력하면 쿠폰 사용 상태가 업데이트된다")
    void updateUserCouponUseYn_Success() {
        // given
        given(couponRepository.getUserCoupon(1L)).willReturn(userCoupon);
        given(couponRepository.saveUserCoupon(userCoupon)).willReturn(userCoupon);

        // when
        UserCouponReponse response = userCouponService.updateUserCouponUseYn(1L, UserCouponType.Y);

        // then
        assertThat(response.couponId()).isEqualTo(1L);
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.useYn()).isEqualTo(UserCouponType.Y);

        verify(couponRepository).getUserCoupon(1L);
        verify(couponRepository).saveUserCoupon(userCoupon);
    }

    @Test
    @DisplayName("존재하지 않는 사용자 쿠폰 ID를 입력하면 CustomExceptionHandler를 반환한다")
    void updateUserCouponUseYn_NotFound() {
        // given
        given(couponRepository.getUserCoupon(1L)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> userCouponService.updateUserCouponUseYn(1L, UserCouponType.Y))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.COUPON_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("사용자 쿠폰 상태 업데이트 중 저장 실패 시 CustomExceptionHandler를 반환한다")
    void updateUserCouponUseYn_SaveFailed() {
        // given
        given(couponRepository.getUserCoupon(1L)).willReturn(userCoupon);
        given(couponRepository.saveUserCoupon(userCoupon)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> userCouponService.updateUserCouponUseYn(1L, UserCouponType.Y))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.COUPON_USE_FAILED.getMessage());
    }


}