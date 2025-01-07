package kr.hhplus.be.server.api.coupon.domain.service;

import kr.hhplus.be.server.api.coupon.domain.entity.UserCoupon;
import kr.hhplus.be.server.api.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.api.coupon.presentation.dto.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final CouponRepository couponRepository;

    public CouponResponseDTO downloadUserCoupon(long userId, long couponId) {

        UserCoupon userCoupon = UserCoupon.createUserCoupon(userId, couponId);

        couponRepository.saveUserCoupon(userCoupon);

        return new CouponResponseDTO();
    }

}
