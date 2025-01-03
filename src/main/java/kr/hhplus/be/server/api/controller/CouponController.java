package kr.hhplus.be.server.api.controller;

import kr.hhplus.be.server.api.dto.CouponRequestDTO;
import kr.hhplus.be.server.api.dto.CouponResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CouponController {

    @GetMapping("/api/coupons")
    public ResponseEntity<List<CouponResponseDTO>> getCoupons() {

        //예제 데이터
        List<CouponResponseDTO> responseList = new ArrayList<>();
        CouponResponseDTO coupon1 = new CouponResponseDTO();
        coupon1.setCouponId(1);
        coupon1.setCouponName("쿠폰명1");
        coupon1.setCouponQuantity(10000);

        CouponResponseDTO coupon2 = new CouponResponseDTO();
        coupon2.setCouponId(2);
        coupon2.setCouponName("쿠폰명2");
        coupon2.setCouponQuantity(10000);

        responseList.add(coupon1);
        responseList.add(coupon2);

        // 리스트 반환
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @PostMapping("/api/coupons/download")
    public ResponseEntity<CouponResponseDTO> couponDownload(
            @RequestBody CouponRequestDTO couponRequestDTO) {

        CouponResponseDTO response = new CouponResponseDTO();

        response.setUserCouponId(1);
        response.setDownloadResult(true);

        if (couponRequestDTO.getUserId() <= 0) {
            response.setMessage("존재하지 않는 유저입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (couponRequestDTO.getUserId() == 1) {
            response.setMessage("존재하지 않는 쿠폰입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (couponRequestDTO.getUserId() == 2) {
            response.setMessage("만료된 쿠폰입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/coupons/{couponId}")
    public ResponseEntity<CouponResponseDTO> getCoupon(
            @PathVariable(name = "couponId") int couponId) {

        CouponResponseDTO response = new CouponResponseDTO();

        response.setCouponId(couponId);
        response.setCouponName("쿠폰명");
        response.setCouponType("PERCENT");
        response.setCouponQuantity(10000);
        response.setCouponAmount(1000);
        response.setExpirationDate(LocalDateTime.of(2024, 12, 31, 23, 59, 59));
        response.setMinUsageAmount(10000);
        response.setMaxDiscountAmount(1000);

        if (couponId <= 0) {
            response.setMessage("쿠폰이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
