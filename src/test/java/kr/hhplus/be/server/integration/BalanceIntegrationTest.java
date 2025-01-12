package kr.hhplus.be.server.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.presentation.controller.BalanceController;
import kr.hhplus.be.server.balance.presentation.dto.BalanceChargeRequestDTO;
import kr.hhplus.be.server.balance.presentation.usecase.BalanceUsecase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@SpringBootTest
//@Transactional
//@Sql("/userData.sql")
@WebMvcTest(BalanceController.class)
@ActiveProfiles("test")
public class BalanceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BalanceService balanceService;

    @MockitoBean
    private BalanceUsecase balanceUsecase;


    @Test
    void userId와_amount입력시_잔액충전후_BalanceResponseDTO반환() throws Exception {
        // Given
        int userId = 1; // data.sql에서 삽입된 유저 ID
        long chargeAmount = 500L;

        BalanceChargeRequestDTO balanceChargeRequestDTO = new BalanceChargeRequestDTO(chargeAmount);

        mockMvc.perform(post("/api/balances/{userId}/charge", 1L)
                        .content(objectMapper.writeValueAsString(balanceChargeRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.balance").value(1500L));

    }

}
