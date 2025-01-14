package kr.hhplus.be.server.balance.domain.service.service;

import kr.hhplus.be.server.balance.domain.entity.User;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.domain.service.request.BalanceDecreaseRequest;
import kr.hhplus.be.server.balance.domain.service.request.BalanceRequest;
import kr.hhplus.be.server.balance.domain.service.response.BalanceChargeInfo;
import kr.hhplus.be.server.balance.domain.service.response.BalanceInfo;
import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceService balanceService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.createUser("testname", 1000L);
        ReflectionTestUtils.setField(user, "userId", 1L);
    }

    @Test
    @DisplayName("사용자 ID를 입력하면 BalanceInfo를 반환한다")
    void getUserBalance_Success() {
        // given
        given(balanceRepository.getUser(1L)).willReturn(user);

        // when
        BalanceInfo response = balanceService.getUserBalance(1L);

        // then
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.balance()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID를 입력하면 CustomExceptionHandler를 반환한다")
    void getUserBalance_UserNotFound() {
        // given
        given(balanceRepository.getUser(1L)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> balanceService.getUserBalance(1L))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("사용자 ID와 금액을 입력하면 잔액 충전 후 BalanceChargeInfo를 반환한다")
    void chargeUserBalance_Success() {
        // given
        given(balanceRepository.getUser(1L)).willReturn(user);
        given(balanceRepository.saveUser(user)).willReturn(user);

        // when
        BalanceChargeInfo response = balanceService.chargeUserBalance(1L, new BalanceRequest(1000L));

        // then
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.balance()).isEqualTo(2000L);
    }

    @Test
    @DisplayName("사용자 ID와 금액을 입력했지만 저장 결과가 null이면 CustomExceptionHandler를 반환한다")
    void chargeUserBalance_ChargeFailed() {
        // given
        given(balanceRepository.getUser(1L)).willReturn(user);
        given(balanceRepository.saveUser(user)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> balanceService.chargeUserBalance(1L, new BalanceRequest(1000L)))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.BALANCE_CHARGE_FAILED.getMessage());
    }

    @Test
    @DisplayName("사용자 ID와 차감 금액을 입력하면 잔액 차감 후 BalanceInfo를 반환한다")
    void decreaseBalance_Success() {
        // given
        given(balanceRepository.getUserWithLock(1L)).willReturn(user);
        given(balanceRepository.saveUser(user)).willReturn(user);

        // when
        BalanceInfo response = balanceService.decreaseBalance(new BalanceDecreaseRequest(1L, 500L));

        // then
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.balance()).isEqualTo(500L);
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID로 잔액 차감을 요청하면 CustomExceptionHandler를 반환한다")
    void decreaseBalance_UserNotFound() {
        // given
        given(balanceRepository.getUserWithLock(1L)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> balanceService.decreaseBalance(new BalanceDecreaseRequest(1L, 500L)))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("사용자 ID와 차감 금액을 입력했으나 잔액이 부족하면 CustomExceptionHandler를 반환한다")
    void decreaseBalance_InsufficientBalance() {
        // given
        user.addBalance(-1000L); // 잔액 부족 상태로 설정
        given(balanceRepository.getUserWithLock(1L)).willReturn(user);

        // when & then
        assertThatThrownBy(() -> balanceService.decreaseBalance(new BalanceDecreaseRequest(1L, 2000L)))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.BALANCE_IS_NOT_ENOUGH.getMessage());
    }

    @Test
    @DisplayName("사용자 ID와 차감 금액을 입력했으나 저장 결과가 null이면 CustomExceptionHandler를 반환한다")
    void decreaseBalance_DecreaseFailed() {
        // given
        given(balanceRepository.getUserWithLock(1L)).willReturn(user);
        given(balanceRepository.saveUser(user)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> balanceService.decreaseBalance(new BalanceDecreaseRequest(1L, 500L)))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.BALANCE_USE_FAILED.getMessage());
    }

}