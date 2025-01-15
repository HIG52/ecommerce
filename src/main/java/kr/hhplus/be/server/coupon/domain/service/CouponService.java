package kr.hhplus.be.server.coupon.domain.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.domain.service.info.CouponInfo;
import kr.hhplus.be.server.coupon.domain.service.info.CouponsInfo;
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

    public CouponInfo getCoupon(long couponId) {
        Coupon coupon = couponRepository.getCoupon(couponId);

        if(coupon == null) {
            throw new CustomExceptionHandler(ErrorCode.COUPON_NOT_FOUND);
        }

        return new CouponInfo(
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
    public CouponInfo getCouponLock(long couponId) {
        try {
            // 쿠폰 조회 및 락 설정
            Coupon coupon = couponRepository.getCouponWithLock(couponId);

            if (coupon == null) {
                throw new CustomExceptionHandler(ErrorCode.COUPON_NOT_FOUND);
            }

            // 쿠폰 재고 확인
            if (coupon.getCouponQuantity() <= 0) {
                throw new CustomExceptionHandler(ErrorCode.COUPON_OUT_OF_STOCK);
            }

            // 쿠폰 수량 감소
            coupon.decreaseQuantity();
            couponRepository.saveCoupon(coupon);

            // 응답 반환
            return new CouponInfo(
                    coupon.getCouponId(),
                    coupon.getCouponName(),
                    coupon.getCouponAmount(),
                    coupon.getCouponType(),
                    coupon.getCouponQuantity(),
                    coupon.getExpirationDate(),
                    coupon.getMaxDiscountAmount(),
                    coupon.getMinUsageAmount()
            );
        } catch (CustomExceptionHandler e) {
            throw e;
        } catch (Exception e) {
            // 시스템 예외 처리
            throw new CustomExceptionHandler(ErrorCode.COUPON_SAVE_FAILED, e);
        }
    }

    public List<CouponsInfo> getCoupons(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Coupon> couponPage = couponRepository.findAll(pageable);

        return couponPage.stream()
                .map(coupon -> new CouponsInfo(
                        coupon.getCouponId(),
                        coupon.getCouponName(),
                        coupon.getCouponQuantity()
                ))
                .toList();
    }

}
