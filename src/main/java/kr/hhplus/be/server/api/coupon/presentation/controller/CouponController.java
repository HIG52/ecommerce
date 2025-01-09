package kr.hhplus.be.server.api.coupon.presentation.controller;

import kr.hhplus.be.server.api.coupon.domain.service.CouponService;
import kr.hhplus.be.server.api.coupon.domain.service.UserCouponService;
import kr.hhplus.be.server.api.coupon.domain.service.request.CouponRequest;
import kr.hhplus.be.server.api.coupon.domain.service.response.CouponResponse;
import kr.hhplus.be.server.api.coupon.domain.service.response.CouponsResponse;
import kr.hhplus.be.server.api.coupon.domain.service.response.UserCouponReponse;
import kr.hhplus.be.server.api.coupon.presentation.dto.CouponRequestDTO;
import kr.hhplus.be.server.api.coupon.presentation.dto.CouponResponseDTO;
import kr.hhplus.be.server.api.coupon.presentation.dto.UserCouponResponseDTO;
import kr.hhplus.be.server.api.coupon.presentation.dto.CouponsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final UserCouponService userCouponService;

    @GetMapping("/api/coupons")
    public ResponseEntity<List<CouponsResponseDTO>> getCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<CouponsResponse> coupons = couponService.getCoupons(page, size);

        // CouponsResponse -> CouponsResponseDTO 변환
        List<CouponsResponseDTO> couponDTOs = coupons.stream()
                .map(coupon -> new CouponsResponseDTO(
                        coupon.couponId(),
                        coupon.couponName(),
                        coupon.couponQuantity()
                ))
                .toList();

        // 리스트 반환
        return ResponseEntity.status(HttpStatus.OK).body(couponDTOs);
    }

    @PostMapping("/api/coupons/download")
    public ResponseEntity<UserCouponResponseDTO> couponDownload(
            @RequestBody CouponRequestDTO couponRequestDTO) {

        CouponRequest couponRequest = new CouponRequest(
                couponRequestDTO.userId(),
                couponRequestDTO.couponId()
        );

        UserCouponReponse userCouponReponse = userCouponService.downloadUserCoupon(couponRequest);

        UserCouponResponseDTO userCouponResponseDTO = new UserCouponResponseDTO(
                userCouponReponse.userId(),
                userCouponReponse.couponId(),
                userCouponReponse.useYn()
        );

        return ResponseEntity.status(HttpStatus.OK).body(userCouponResponseDTO);
    }

    @GetMapping("/api/coupons/{couponId}")
    public ResponseEntity<CouponResponseDTO> getCoupon(
            @PathVariable(name = "couponId") int couponId) {

        CouponResponse couponResponse = couponService.getCoupon(couponId);

        CouponResponseDTO couponResponseDTO = new CouponResponseDTO(
                couponResponse.couponId(),
                couponResponse.couponName(),
                couponResponse.couponAmount(),
                couponResponse.couponType(),
                couponResponse.couponQuantity(),
                couponResponse.expirationDate(),
                couponResponse.maxDiscountAmount(),
                couponResponse.minUsageAmount()
        );

        return ResponseEntity.status(HttpStatus.OK).body(couponResponseDTO);
    }

}
