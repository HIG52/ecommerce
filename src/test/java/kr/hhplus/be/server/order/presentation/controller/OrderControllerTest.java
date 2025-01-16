package kr.hhplus.be.server.order.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import kr.hhplus.be.server.order.presentation.dto.OrderRequestDTO;
import kr.hhplus.be.server.order.presentation.usecase.OrderUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql("/orderData.sql")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderUsecase orderUsecase;

    @Test
    @DisplayName("POST /api/orders/ 요청시 주문을 등록하고 주문정보를 반환한다.")
    void createOrders() throws Exception {

        // given
        // 요청 DTO 준비
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(
                10L,                     // userId
                2800000L,                 // orderTotalAmount
                List.of(1L, 2L),        // productIds
                List.of(1, 2),         // productQuantities
                List.of(1200000L, 800000L)   // productPrices
        );

        // when & then
        mockMvc.perform(post("/api/orders/")
                        .content(objectMapper.writeValueAsString(orderRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.orderTotalPrice").value(2800000L))
                .andExpect(jsonPath("$.paymentStatus").value(PaymentStatusType.PENDING.toString()))
                .andExpect(jsonPath("$.status").value(OrderStatusType.ORDERED.toString()))
                .andExpect(jsonPath("$.orderQuantity").value(3))
                .andExpect(jsonPath("$.orderAmount").value(2800000L));

    }

}