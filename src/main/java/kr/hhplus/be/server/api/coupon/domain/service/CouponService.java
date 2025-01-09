package kr.hhplus.be.server.api.coupon.domain.service;

import kr.hhplus.be.server.api.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.api.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.api.coupon.domain.service.response.CouponResponse;
import kr.hhplus.be.server.api.coupon.domain.service.response.CouponsResponse;
import kr.hhplus.be.server.api.product.domain.entity.Product;
import kr.hhplus.be.server.api.product.presentation.dto.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponResponse getCoupon(long couponId) {
        Coupon coupon = couponRepository.getCoupon(couponId);

        if(coupon == null) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }

        return new CouponResponse(
                coupon.getCouponId(),
                coupon.getCouponName(),
                coupon.getCouponAmount(),
                coupon.getCouponType(),
                coupon.getCouponQuantity(),
                coupon.getExpirationDate(),
                coupon.getMaxDiscountAmount(),
                coupon.getMinUsageAmount()
        );
    }

    public List<CouponsResponse> getCoupons(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Coupon> couponPage = couponRepository.findAll(pageable);

        return couponPage.stream()
                .map(coupon -> new CouponsResponse(
                        coupon.getCouponId(),
                        coupon.getCouponName(),
                        coupon.getCouponQuantity()
                ))
                .toList();
    }

}
