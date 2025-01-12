package kr.hhplus.be.server.balance.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.balance.domain.entity.User;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.presentation.dto.BalanceChargeRequestDTO;
import kr.hhplus.be.server.balance.presentation.dto.BalanceChargeResponseDTO;
import kr.hhplus.be.server.balance.presentation.usecase.BalanceUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BalanceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private BalanceUsecase balanceUsecase;


    @Test
    @DisplayName("POST /api/balances/{userId}/charge - 정상적인 요청에 대해 잔액 충전 성공")
    void userPointCharge_Success() throws Exception {
        // given
        long userId = 1L;
        long chargeAmount = 1000L;
        User user = User.createUser("test", 1000L);
        balanceRepository.saveUser(user); // 초기 잔액
        BalanceChargeRequestDTO requestDTO = new BalanceChargeRequestDTO(chargeAmount);
        BalanceChargeResponseDTO expectedResponse = new BalanceChargeResponseDTO(userId, 2000L); // 충전 후 잔액

        // when & then
        mockMvc.perform(post("/api/balances/{userId}/charge", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.balance").value(expectedResponse.balance()));
    }

}
