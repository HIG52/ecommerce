package kr.hhplus.be.server.balance.domain.service.service;

import kr.hhplus.be.server.balance.domain.entity.UserBalanceHistory;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.BalanceHistoryService;
import kr.hhplus.be.server.balance.domain.service.request.BalanceHistoryRequest;
import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.common.type.HistoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BalanceHistoryServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceHistoryService balanceHistoryService;

    @Test
    @DisplayName("사용자 아이디, 저장타입, 잔액을 입력하면 잔액 히스토리가 정상적으로 저장된다")
    void saveBalanceHistory_Success() {
        // given
        long userId = 1L;
        BalanceHistoryRequest request = new BalanceHistoryRequest(1000L, HistoryType.USE);

        // stubbing
        given(balanceRepository.saveUserBalanceHistory(any(UserBalanceHistory.class)))
                .willReturn(UserBalanceHistory.createUserBalanceHistory(userId, request.historyType(), request.amount()));

        // when
        balanceHistoryService.saveBalanceHistory(userId, request);

        // then
        verify(balanceRepository).saveUserBalanceHistory(any(UserBalanceHistory.class)); // 호출 검증
    }

    @Test
    @DisplayName("저장 중 예외가 발생하면 CustomExceptionHandler를 반환한다")
    void saveBalanceHistory_SaveFailed() {
        // given
        long userId = 1L;
        BalanceHistoryRequest request = new BalanceHistoryRequest(1000L, HistoryType.USE);
        UserBalanceHistory history = UserBalanceHistory.createUserBalanceHistory(userId, request.historyType(), request.amount());

        doThrow(new RuntimeException("DB 에러"))
                .when(balanceRepository).saveUserBalanceHistory(history);

        // when & then
        assertThatThrownBy(() -> balanceHistoryService.saveBalanceHistory(userId, request))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessageContaining(ErrorCode.BALANCE_HISTORY_SAVE_FAILED.getMessage());
    }
}