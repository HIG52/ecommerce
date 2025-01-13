package kr.hhplus.be.server.coupon.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.coupon.presentation.dto.CouponRequestDTO;
import kr.hhplus.be.server.coupon.presentation.dto.CouponResponseDTO;
import kr.hhplus.be.server.coupon.presentation.dto.CouponsResponseDTO;
import kr.hhplus.be.server.coupon.presentation.dto.UserCouponResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coupon API", description = "쿠폰 관리 API")
public interface CouponControllerDocs {

    @Operation(
            summary = "쿠폰 목록 조회",
            description = "쿠폰 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "쿠폰목록 조회 성공",
                            content = @Content(schema = @Schema(implementation = CouponsResponseDTO.class)))
            }
    )
    public ResponseEntity<List<CouponsResponseDTO>> getCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(
            summary = "쿠폰 발급",
            description = "쿠폰을 발급합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "쿠폰 발급 성공",
                            content = @Content(schema = @Schema(implementation = UserCouponResponseDTO.class)))
            }
    )
    public ResponseEntity<UserCouponResponseDTO> couponDownload(
            @RequestBody CouponRequestDTO couponRequestDTO);

    @Operation(
            summary = "쿠폰 상세 조회",
            description = "쿠폰 상세 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "쿠폰 상세조회 성공",
                            content = @Content(schema = @Schema(implementation = CouponResponseDTO.class)))
            }
    )
    public ResponseEntity<CouponResponseDTO> getCoupon(
            @PathVariable(name = "couponId") int couponId);
}
