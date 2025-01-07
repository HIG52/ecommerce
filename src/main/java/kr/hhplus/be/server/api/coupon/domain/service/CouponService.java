package kr.hhplus.be.server.api.coupon.domain.service;

import kr.hhplus.be.server.api.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.api.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.api.coupon.presentation.dto.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository CouponRepository;

    public CouponResponseDTO getCoupon(long couponId) {
        Coupon coupon = CouponRepository.getCoupon(couponId);

        CouponResponseDTO couponResponseDTO = new CouponResponseDTO();
        couponResponseDTO.setCouponName(coupon.getCouponName());
        couponResponseDTO.setCouponAmount(coupon.getCouponAmount());
        couponResponseDTO.setCouponType(coupon.getCouponType());
        couponResponseDTO.setCouponQuantity(coupon.getCouponQuantity());
        couponResponseDTO.setExpirationDate(coupon.getExpirationDate());
        couponResponseDTO.setMaxDiscountAmount(coupon.getMaxDiscountAmount());
        couponResponseDTO.setMinUsageAmount(coupon.getMinUsageAmount());

        return couponResponseDTO;
    }

}
