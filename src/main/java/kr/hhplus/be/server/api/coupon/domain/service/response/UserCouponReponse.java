package kr.hhplus.be.server.api.coupon.domain.service.response;

import kr.hhplus.be.server.common.type.UserCouponType;

public record UserCouponReponse(
        long couponId,
        long userId,
        UserCouponType useYn
) {
}
