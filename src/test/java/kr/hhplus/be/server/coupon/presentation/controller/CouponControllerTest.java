package kr.hhplus.be.server.coupon.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.common.type.CouponType;
import kr.hhplus.be.server.coupon.domain.service.CouponService;
import kr.hhplus.be.server.coupon.presentation.dto.CouponRequestDTO;
import kr.hhplus.be.server.coupon.presentation.usecase.CouponUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/couponData.sql")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponUsecase couponUsecase;

    @Test
    @DisplayName("GET /api/coupons 요청시 쿠폰 목록을 조회한다.")
    void getCoupons() throws Exception {

        int page = 0;
        int size = 10;

        mockMvc.perform(get("/api/coupons")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].couponId").value(1L))
                .andExpect(jsonPath("$[0].couponName").value("New Year Sale"));


    }

    @Test
    @DisplayName("POST /api/coupons/download 요청시 쿠폰을 다운로드한다.")
    void couponDownload() throws Exception {
        long couponId = 2L;
        long userId = 1L;
        CouponRequestDTO couponRequestDTO = new CouponRequestDTO(userId, couponId);

        mockMvc.perform(post("/api/coupons/download")
                .content(objectMapper.writeValueAsString(couponRequestDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.couponId").value(2L))
                .andExpect(jsonPath("$.useYn").value("N"));


    }

    @Test
    @DisplayName("GET /api/coupons/{couponId} 요청시 쿠폰 상세정보를 반환한다.")
    void getCoupon() throws Exception {
        long couponId = 1L;

        mockMvc.perform(get("/api/coupons/{couponId}", couponId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponId").value(1L))
                .andExpect(jsonPath("$.couponName").value("New Year Sale"))
                .andExpect(jsonPath("$.couponAmount").value(5000L))
                .andExpect(jsonPath("$.couponType").value(CouponType.AMOUNT.toString()))
                .andExpect(jsonPath("$.couponQuantity").value(5L))
                .andExpect(jsonPath("$.expirationDate").value("2025-12-31T23:59:59"))
                .andExpect(jsonPath("$.maxDiscountAmount").value(20000L))
                .andExpect(jsonPath("$.minUsageAmount").value(10000L));

    }
}