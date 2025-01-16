package kr.hhplus.be.server.payment.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.payment.presentation.dto.PaymentRequestDTO;
import kr.hhplus.be.server.payment.presentation.usecase.PaymentUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/paymentData.sql")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentUsecase paymentUsecase;

    @Test
    @DisplayName("POST /api/payments/ 요청시 결제를 생성하고 결제정보를 반환한다.")
    void payments() throws Exception{

        // given
        // 요청 DTO 준비
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(1L, 1L, 1L, 5000L);

        // when & then
        mockMvc.perform(post("/api/payments/")
                        .content(objectMapper.writeValueAsString(paymentRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(1L));

    }

}