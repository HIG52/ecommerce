package kr.hhplus.be.server.coupon.domain.service;

import kr.hhplus.be.server.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.domain.service.response.CouponResponse;
import kr.hhplus.be.server.coupon.domain.service.response.CouponsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public CouponResponse getCouponLock(long couponId) {
        Coupon coupon = couponRepository.getCouponWithLock(couponId);
        if(coupon == null) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }

        if(coupon.getCouponQuantity() <= 0) {
            throw new IllegalArgumentException("쿠폰 재고가 없습니다.");
        }

        coupon.decreaseQuantity();
        couponRepository.saveCoupon(coupon);

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
