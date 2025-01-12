package kr.hhplus.be.server.coupon.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.coupon.domain.service.CouponService;
import kr.hhplus.be.server.coupon.domain.service.response.CouponResponse;
import kr.hhplus.be.server.coupon.domain.service.response.CouponsResponse;
import kr.hhplus.be.server.coupon.presentation.dto.CouponRequestDTO;
import kr.hhplus.be.server.coupon.presentation.dto.CouponResponseDTO;
import kr.hhplus.be.server.coupon.presentation.dto.UserCouponResponseDTO;
import kr.hhplus.be.server.coupon.presentation.dto.CouponsResponseDTO;
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

        UserCouponResponseDTO userCouponResponseDTO = couponUsecase.downloadUserCoupon(couponRequestDTO);

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
