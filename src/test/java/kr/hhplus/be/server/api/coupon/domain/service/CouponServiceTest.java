package kr.hhplus.be.server.api.coupon.domain.service;

import kr.hhplus.be.server.api.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.api.coupon.domain.entity.UserCoupon;
import kr.hhplus.be.server.api.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.api.coupon.domain.service.request.CouponRequest;
import kr.hhplus.be.server.api.coupon.domain.service.response.CouponResponse;
import kr.hhplus.be.server.api.coupon.domain.service.response.CouponsResponse;
import kr.hhplus.be.server.api.coupon.domain.service.response.UserCouponReponse;
import kr.hhplus.be.server.common.type.CouponType;
import kr.hhplus.be.server.common.type.UserCouponType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Test
    void couponId를_입력하면_쿠폰상세_반환() {
        // given
        CouponService couponService = new CouponService(couponRepository);
        long couponId = 1L;
        Coupon coupon = Coupon.createCoupon(
                "TestCoupon",
                CouponType.AMOUNT,
                1000L,
                50,
                java.time.LocalDateTime.now().plusDays(10),
                500L,
                2000L
        );

        ReflectionTestUtils.setField(coupon, "couponId", 1L);

        given(couponRepository.getCoupon(couponId))
                .willReturn(coupon);

        // when
        CouponResponse couponResponse = couponService.getCoupon(couponId);

        // then
        assertEquals(couponResponse.couponName(), "TestCoupon");
        assertEquals(couponResponse.couponType(), CouponType.AMOUNT);
        assertEquals(couponResponse.couponAmount(), 1000L);
        assertEquals(couponResponse.couponQuantity(), 50);
        assertEquals(couponResponse.minUsageAmount(), 500L);
        assertEquals(couponResponse.maxDiscountAmount(), 2000L);
    }

    @Test
    void couponId가_존재하지_않으면_IllegalArgumentException발생() {
        // given
        CouponService couponService = new CouponService(couponRepository);
        long couponId = 1L;

        // Mock 설정: 쿠폰 조회 시 null 반환
        given(couponRepository.getCoupon(couponId)).willReturn(null);

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> couponService.getCoupon(couponId));
        assertEquals("존재하지 않는 쿠폰입니다.", exception.getMessage());
    }

    @Test
    void 페이지_번호와_사이즈를_입력하면_쿠폰_리스트를_페이지로_반환한다() {
        // given
        CouponService couponService = new CouponService(couponRepository);
        int page = 0;
        int size = 5;

        Coupon coupon1 = Coupon.createCoupon(
                "Coupon1", CouponType.AMOUNT, 1000L, 10, null, null, null);
        ReflectionTestUtils.setField(coupon1, "couponId", 1L);

        Coupon coupon2 = Coupon.createCoupon(
                "Coupon2", CouponType.AMOUNT, 2000L, 20, null, null, null);
        ReflectionTestUtils.setField(coupon2, "couponId", 2L);

        Coupon coupon3 = Coupon.createCoupon(
                "Coupon3", CouponType.AMOUNT, 3000L, 30, null, null, null);
        ReflectionTestUtils.setField(coupon3, "couponId", 3L);

        List<Coupon> coupons = List.of(coupon1, coupon2, coupon3);

        Page<Coupon> couponPage = new PageImpl<>(coupons, PageRequest.of(page, size), coupons.size());

        given(couponRepository.findAll(PageRequest.of(page, size))).willReturn(couponPage);

        // when
        List<CouponsResponse> result = couponService.getCoupons(page, size);

        // then
        assertThat(result).hasSize(3);

        assertThat(result.get(0).couponId()).isEqualTo(1L);
        assertThat(result.get(0).couponName()).isEqualTo("Coupon1");
        assertThat(result.get(0).couponQuantity()).isEqualTo(10);

        assertThat(result.get(1).couponId()).isEqualTo(2L);
        assertThat(result.get(1).couponName()).isEqualTo("Coupon2");
        assertThat(result.get(1).couponQuantity()).isEqualTo(20);

        assertThat(result.get(2).couponId()).isEqualTo(3L);
        assertThat(result.get(2).couponName()).isEqualTo("Coupon3");
        assertThat(result.get(2).couponQuantity()).isEqualTo(30);

        // findAll 호출 검증
        verify(couponRepository).findAll(PageRequest.of(page, size));
    }

    @Test
    void 이미_발급받은_쿠폰이면_예외_발생() {
        // given
        UserCouponService userCouponService = new UserCouponService(couponRepository);
        long userId = 1L;
        long couponId = 100L;
        CouponRequest request = new CouponRequest(userId, couponId);

        // 가짜로 '이미 발급받은 쿠폰' 이 존재한다고 Mock 설정
        UserCoupon existingUserCoupon = UserCoupon.createUserCoupon(userId, couponId, UserCouponType.N);
        ReflectionTestUtils.setField(existingUserCoupon, "userId", userId);
        ReflectionTestUtils.setField(existingUserCoupon, "couponId", couponId);

        given(couponRepository.getMyUserCoupon(userId, couponId))
                .willReturn(existingUserCoupon);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                userCouponService.downloadUserCoupon(request)
        );
        assertThat(exception.getMessage()).isEqualTo("이미 발급받은 쿠폰입니다.");

        // 이미 발급받은 경우 saveUserCoupon()은 절대 호출되지 않아야 함
        verify(couponRepository, never()).saveUserCoupon(any());
    }

    @Test
    void 쿠폰이_발급되지_않았다면_정상_발급된다() {
        // given
        UserCouponService userCouponService = new UserCouponService(couponRepository);
        long userId = 2L;
        long couponId = 200L;
        CouponRequest request = new CouponRequest(userId, couponId);

        // 유저가 이 쿠폰을 가지고 있지 않다고 Mock 설정
        given(couponRepository.getMyUserCoupon(userId, couponId))
                .willReturn(null);

        // 새로 발급될 UserCoupon 엔티티(리턴될 값)도 가짜로 설정
        UserCoupon newUserCoupon = UserCoupon.createUserCoupon(couponId, userId, UserCouponType.N);
        ReflectionTestUtils.setField(newUserCoupon, "userCouponId", 999L); // PK 가정

        // repository.saveUserCoupon() 호출 시 newUserCoupon 을 리턴하게 Mock 설정
        given(couponRepository.saveUserCoupon(any(UserCoupon.class)))
                .willReturn(newUserCoupon);

        // when
        UserCouponReponse response = userCouponService.downloadUserCoupon(request);

        // then
        assertThat(response.userId()).isEqualTo(userId);
        assertThat(response.couponId()).isEqualTo(couponId);
        assertThat(response.useYn()).isEqualTo(UserCouponType.N);

        // saveUserCoupon()은 1번 호출되어야 함
        verify(couponRepository).saveUserCoupon(any(UserCoupon.class));
    }


}