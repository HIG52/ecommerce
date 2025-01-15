package kr.hhplus.be.server.coupon.domain.service.info;

import kr.hhplus.be.server.common.type.UserCouponType;

public record UserCouponInfo(
        long couponId,
        long userId,
        UserCouponType useYn
) {
}
