package kr.hhplus.be.server.api.coupon.domain.service;

import kr.hhplus.be.server.api.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.api.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.api.coupon.presentation.dto.CouponResponseDTO;
import kr.hhplus.be.server.common.type.CouponType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Test
    void couponId를_입력하면_쿠폰상세_반환() {
        // given
        CouponService couponService = new CouponService(couponRepository);
        long couponId = 1L;
        given(couponRepository.getCoupon(couponId))
                .willReturn(Coupon.createCoupon(
                        "TestCoupon",
                        CouponType.AMOUNT,
                        1000L,
                        50,
                        java.time.LocalDateTime.now().plusDays(10),
                        500L,
                        2000L
                ));

        // when
        CouponResponseDTO couponResponseDTO = couponService.getCoupon(couponId);

        // then
        assertEquals(couponResponseDTO.getCouponName(), "TestCoupon");
        assertEquals(couponResponseDTO.getCouponType(), CouponType.AMOUNT);
        assertEquals(couponResponseDTO.getCouponAmount(), 1000L);
        assertEquals(couponResponseDTO.getCouponQuantity(), 50);
        assertEquals(couponResponseDTO.getMinUsageAmount(), 500L);
        assertEquals(couponResponseDTO.getMaxDiscountAmount(), 2000L);
    }
}