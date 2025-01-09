package kr.hhplus.be.server.api.coupon.domain.service;

import kr.hhplus.be.server.api.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.api.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.api.coupon.domain.service.response.CouponResponse;
import kr.hhplus.be.server.api.coupon.domain.service.response.CouponsResponse;
import kr.hhplus.be.server.common.type.CouponType;
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
import static org.mockito.BDDMockito.given;
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
}