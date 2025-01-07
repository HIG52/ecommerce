package kr.hhplus.be.server.domain.service;

import kr.hhplus.be.server.api.balance.domain.service.BalanceHistoryService;
import kr.hhplus.be.server.api.balance.domain.service.BalanceService;
import kr.hhplus.be.server.common.type.HistoryType;
import kr.hhplus.be.server.api.balance.presentation.dto.BalanceRequestDTO;
import kr.hhplus.be.server.api.balance.presentation.dto.BalanceResponseDTO;
import kr.hhplus.be.server.api.balance.domain.entity.User;
import kr.hhplus.be.server.api.balance.domain.repository.BalanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private BalanceRepository balanceRepository;


    @Test
    void UserId를_입력하면_잔액_반환() {
        // given
        long userId = 1L;
        BalanceService balanceService = new BalanceService(balanceRepository);
        User user = User.createUser("test", 20000L);


        given(balanceRepository.getUser(userId)).willReturn(user);

        // when
        BalanceResponseDTO balanceResponseDTO = balanceService.getUserBalance(userId);

        // then
        assertThat(balanceResponseDTO.getUserId()).isEqualTo(userId);
        assertThat(balanceResponseDTO.getBalance()).isEqualTo(20000L);
    }

    @Test
    void UserId와_amount를_입력하면_히스토리를_저장() {
        // given
        long userId = 1L;
        long amount = 1000L;
        HistoryType historyType = HistoryType.CHARGE;

        BalanceHistoryService balanceHistoryService = new BalanceHistoryService(balanceRepository);

        //when

        //then

    }

    @Test
    void UserId와_충전금액을_입력하면_잔액_반환() {
        // given
        long userId = 1L;
        long amount = 10000L;
        BalanceService balanceService = new BalanceService(balanceRepository);
        User user = User.createUser("test", 20000L);
        BalanceRequestDTO balanceRequestDTO = new BalanceRequestDTO();
        balanceRequestDTO.setUserId(userId);
        balanceRequestDTO.setAmount(amount);
        given(balanceRepository.getUser(userId)).willReturn(user);

        // when
        BalanceResponseDTO balanceResponseDTO = balanceService.chargeUserBalance(balanceRequestDTO);

        // then
        assertThat(balanceResponseDTO.getUserId()).isEqualTo(userId);
        assertThat(balanceResponseDTO.getBalance()).isEqualTo(30000L);
    }

}