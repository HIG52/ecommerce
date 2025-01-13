package kr.hhplus.be.server.balance.domain.service.service;

import kr.hhplus.be.server.balance.domain.entity.User;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.domain.service.request.BalanceDecreaseRequest;
import kr.hhplus.be.server.balance.domain.service.request.BalanceRequest;
import kr.hhplus.be.server.balance.domain.service.response.BalanceChargeInfo;
import kr.hhplus.be.server.balance.domain.service.response.BalanceInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
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
        BalanceInfo response = balanceService.getUserBalance(userId);

        // then
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.balance()).isEqualTo(1000L);
    }

    @Test
    void userId를_입력했는데_존재하지_않는다면_IllegalArgumentException반환() {
        // given
        BalanceService balanceService = new BalanceService(balanceRepository);

        long userId = 1L;

        given(balanceRepository.getUser(userId)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> balanceService.getUserBalance(userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자 정보가 존재하지 않습니다.");
    }

    @Test
    void userId_amount입력시_잔액충전후_BalanceChargeResponse반환() {
        // given
        BalanceService balanceService = new BalanceService(balanceRepository);

        long userId = 1L;
        User user = User.createUser("testname", 1000L);
        ReflectionTestUtils.setField(user, "userId", 1L);

        given(balanceRepository.getUser(userId)).willReturn(user);
        given(balanceRepository.saveUser(user)).willReturn(user);

        // when
        BalanceChargeInfo response = balanceService.chargeUserBalance(userId, new BalanceRequest(1000L));

        // then
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.balance()).isEqualTo(2000L);
    }

    @Test
    void 잔액을_충전했을때_충전_결과가_null이면_IllegalStateException_발생() {
        // given
        BalanceService balanceService = new BalanceService(balanceRepository);
        long userId = 1L;
        long chargeAmount = 5000L;

        User user = User.createUser("testname", 1000L);
        ReflectionTestUtils.setField(user, "userId", userId);

        BalanceRequest balanceRequest = new BalanceRequest(chargeAmount);

        given(balanceRepository.getUser(userId)).willReturn(user);
        given(balanceRepository.saveUser(user)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> balanceService.chargeUserBalance(userId, balanceRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("잔액 충전 실패: 충전 결과가 올바르지 않습니다.");
    }

    @Test
    void 잔액_차감_요청시_잔액이_성공적으로_차감되어_BalanceResponse반환() {
        // given
        BalanceService balanceService = new BalanceService(balanceRepository);

        long userId = 1L;
        long initialBalance = 5000L;
        long decreaseAmount = 2000L;

        User user = User.createUser("testname", initialBalance);
        ReflectionTestUtils.setField(user, "userId", userId);

        given(balanceRepository.getUserWithLock(userId)).willReturn(user);
        given(balanceRepository.saveUser(user)).willReturn(user);

        // when
        BalanceInfo response = balanceService.decreaseBalance(new BalanceDecreaseRequest(userId, decreaseAmount));

        // then
        assertThat(response.userId()).isEqualTo(userId);
        assertThat(response.balance()).isEqualTo(initialBalance - decreaseAmount);
    }

    @Test
    void 잔액_차감_요청시_사용자가_존재하지_않으면_IllegalArgumentException발생() {
        // given
        BalanceService balanceService = new BalanceService(balanceRepository);

        long userId = 1L;
        long decreaseAmount = 2000L;

        given(balanceRepository.getUserWithLock(userId)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> balanceService.decreaseBalance(new BalanceDecreaseRequest(userId, decreaseAmount)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자 정보가 존재하지 않습니다.");
    }

    @Test
    void 잔액_차감_요청시_잔액이_부족하면_IllegalStateException발생() {
        // given
        BalanceService balanceService = new BalanceService(balanceRepository);

        long userId = 1L;
        long initialBalance = 1000L;
        long decreaseAmount = 2000L;

        User user = User.createUser("testname", initialBalance);
        ReflectionTestUtils.setField(user, "userId", userId);

        given(balanceRepository.getUserWithLock(userId)).willReturn(user);

        // when & then
        assertThatThrownBy(() -> balanceService.decreaseBalance(new BalanceDecreaseRequest(userId, decreaseAmount)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("잔액 차감 실패: 잔액이 부족합니다.");
    }

    @Test
    void 잔액_차감_결과가_null이면_IllegalStateException발생() {
        // given
        BalanceService balanceService = new BalanceService(balanceRepository);

        long userId = 1L;
        long initialBalance = 5000L;
        long decreaseAmount = 2000L;

        User user = User.createUser("testname", initialBalance);
        ReflectionTestUtils.setField(user, "userId", userId);

        given(balanceRepository.getUserWithLock(userId)).willReturn(user);
        given(balanceRepository.saveUser(user)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> balanceService.decreaseBalance(new BalanceDecreaseRequest(userId, decreaseAmount)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("잔액 차감 실패: 차감 결과가 올바르지 않습니다.");
    }

}