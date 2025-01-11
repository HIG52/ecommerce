package kr.hhplus.be.server.integration;

import kr.hhplus.be.server.balance.presentation.controller.BalanceController;
import kr.hhplus.be.server.balance.presentation.dto.BalanceRequestDTO;
import kr.hhplus.be.server.balance.presentation.dto.BalanceResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/userData.sql")
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class BalanceIntegrationTest {

    @Autowired
    private BalanceController balanceController;

    @Test
    void userId와_amount입력시_잔액충전후_BalanceResponseDTO반환() {
        // Given
        int userId = 1; // data.sql에서 삽입된 유저 ID
        long chargeAmount = 500L;

        BalanceRequestDTO balanceRequestDTO = new BalanceRequestDTO(userId, chargeAmount);

        // When
        ResponseEntity<BalanceResponseDTO> response = balanceController.userPointCharge(userId, balanceRequestDTO);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().balance()).isEqualTo(1500L); // 초기 1000 + 500
    }

}
