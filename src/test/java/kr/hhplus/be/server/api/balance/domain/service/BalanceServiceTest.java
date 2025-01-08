package kr.hhplus.be.server.api.balance.domain.service;

import kr.hhplus.be.server.api.balance.domain.entity.User;
import kr.hhplus.be.server.api.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.api.balance.domain.service.request.BalanceRequest;
import kr.hhplus.be.server.api.balance.domain.service.response.BalanceChargeResponse;
import kr.hhplus.be.server.api.balance.domain.service.response.BalanceResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @Test
    void userId를_입력하면_BalanceResponse반환() {
        // given
        BalanceService balanceService = new BalanceService(balanceRepository);

        long userId = 1L;
        User user = User.createUser("testname", 1000L);
        ReflectionTestUtils.setField(user, "userId", 1L);

        given(balanceRepository.getUser(userId)).willReturn(user);

        // when
        BalanceResponse response = balanceService.getUserBalance(userId);

        // then
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.balance()).isEqualTo(1000L);
    }

    @Test
    void chargeUserBalance() {
        // given
        BalanceService balanceService = new BalanceService(balanceRepository);

        long userId = 1L;
        User user = User.createUser("testname", 1000L);
        ReflectionTestUtils.setField(user, "userId", 1L);

        given(balanceRepository.getUser(userId)).willReturn(user);
        given(balanceRepository.saveUser(user)).willReturn(user);

        // when
        BalanceChargeResponse response = balanceService.chargeUserBalance(userId, new BalanceRequest(1000L));

        // then
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.balance()).isEqualTo(2000L);
    }
}