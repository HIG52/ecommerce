package kr.hhplus.be.server.domain.service;

import kr.hhplus.be.server.api.dto.BalanceResponseDTO;
import kr.hhplus.be.server.domain.entity.User;
import kr.hhplus.be.server.domain.repository.BalanceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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
        User user = User.createUser(userId, "test", 20000L);


        given(balanceRepository.getUser(userId)).willReturn(user);

        // when
        BalanceResponseDTO balanceResponseDTO = balanceService.getUserBalance(userId);

        // then
        assertThat(balanceResponseDTO.getUserId()).isEqualTo(userId);
        assertThat(balanceResponseDTO.getBalance()).isEqualTo(20000L);
    }

}