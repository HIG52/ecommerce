package kr.hhplus.be.server.balance.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.domain.service.response.BalanceInfo;
import kr.hhplus.be.server.balance.presentation.dto.BalanceChargeResponseDTO;
import kr.hhplus.be.server.balance.presentation.dto.BalanceChargeRequestDTO;
import kr.hhplus.be.server.balance.presentation.usecase.BalanceUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BalanceController.class)
@ActiveProfiles("test")
class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BalanceService balanceService;

    @MockitoBean
    private BalanceUsecase balanceUsecase;

    @Test
    @DisplayName("GET /api/balances/{userId}/charge 요청시 사용자의 잔액을 충전한다.")
    void userPointChargeTest() throws Exception {
        // Given
        int userId = 1;
        long chargeAmount = 500L;

        BalanceChargeRequestDTO balanceChargeRequestDTO = new BalanceChargeRequestDTO(chargeAmount);

        given(balanceUsecase.chargeUserBalance(userId, balanceChargeRequestDTO)).willReturn(new BalanceChargeResponseDTO(1L, 1500L));

        mockMvc.perform(post("/api/balances/{userId}/charge", 1L)
                        .content(objectMapper.writeValueAsString(balanceChargeRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.balance").value(1500L));

    }

    @Test
    @DisplayName("GET /api/balances/{userId}/balance 요청시 사용자의 잔액을 조회한다.")
    void getUserBalanceTest() throws Exception {
        // Given
        int userId = 1;

        given(balanceService.getUserBalance(userId)).willReturn(new BalanceInfo(1L, 1500L));

        mockMvc.perform(get("/api/balances/{userId}/balance", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1500L));

    }



}