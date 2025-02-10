package kr.hhplus.be.server.coupon.presentation.controller;

import kr.hhplus.be.server.coupon.domain.service.CouponInventoryService;
import kr.hhplus.be.server.coupon.domain.service.CouponService;
import kr.hhplus.be.server.coupon.domain.service.info.CouponInfo;
import kr.hhplus.be.server.coupon.domain.service.info.CouponsInfo;
import kr.hhplus.be.server.coupon.presentation.dto.*;
import kr.hhplus.be.server.coupon.presentation.usecase.CouponUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController implements CouponControllerDocs {

    private final CouponService couponService;
    private final CouponUsecase couponUsecase;
    private final CouponInventoryService couponInventoryService;

    @GetMapping("/api/coupons")
    public ResponseEntity<List<CouponsResponseDTO>> getCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<CouponsInfo> coupons = couponService.getCoupons(page, size);

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

        UserCouponResponseDTO userCouponResponseDTO = couponUsecase.downloadUserCoupon(couponRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(userCouponResponseDTO);
    }

    @GetMapping("/api/coupons/{couponId}")
    public ResponseEntity<CouponResponseDTO> getCoupon(
            @PathVariable(name = "couponId") int couponId) {

        CouponInfo couponInfo = couponService.getCoupon(couponId);

        CouponResponseDTO couponResponseDTO = new CouponResponseDTO(
                couponInfo.couponId(),
                couponInfo.couponName(),
                couponInfo.couponAmount(),
                couponInfo.couponType(),
                couponInfo.couponQuantity(),
                couponInfo.expirationDate(),
                couponInfo.maxDiscountAmount(),
                couponInfo.minUsageAmount()
        );

        return ResponseEntity.status(HttpStatus.OK).body(couponResponseDTO);
    }

    @PostMapping("/api/coupons/issued")
    public ResponseEntity<Boolean> issuedCouponQuantity(
            @RequestBody CouponIssuedRequestDTO couponIssuedRequestDTO) {

        Boolean result = couponInventoryService.issuedCouponQuantity(couponIssuedRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
