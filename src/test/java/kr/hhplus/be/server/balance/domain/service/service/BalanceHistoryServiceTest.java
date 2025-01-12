package kr.hhplus.be.server.balance.domain.service.service;

import kr.hhplus.be.server.balance.domain.entity.UserBalanceHistory;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.BalanceHistoryService;
import kr.hhplus.be.server.balance.domain.service.request.BalanceHistoryRequest;
import kr.hhplus.be.server.balance.domain.service.response.BalanceHistoryResponse;
import kr.hhplus.be.server.common.type.HistoryType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BalanceHistoryServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @Test
    void userId_amount_historyType_입력시_BalanceHistoryResponse반환() {
        // given
        BalanceHistoryService balanceHistoryService = new BalanceHistoryService(balanceRepository);
        long userId = 1L;
        long amount = 5000L;
        HistoryType historyType = HistoryType.CHARGE;

        // UserBalanceHistory 생성
        UserBalanceHistory userBalanceHistory = UserBalanceHistory.createUserBalanceHistory(userId, historyType, amount);

        // 저장소가 반환할 저장 결과 설정
        UserBalanceHistory savedBalanceHistory = UserBalanceHistory.createUserBalanceHistory(userId, historyType, amount);

        // BalanceHistoryRequest 생성
        BalanceHistoryRequest balanceHistoryRequest = new BalanceHistoryRequest(amount, historyType);

        // Mock 설정: saveUserBalanceHistory 호출 시 savedBalanceHistory 반환
        given(balanceRepository.saveUserBalanceHistory(any(UserBalanceHistory.class))).willReturn(savedBalanceHistory);

        // when
        balanceHistoryService.saveBalanceHistory(userId, balanceHistoryRequest);

        // then
        verify(balanceRepository).saveUserBalanceHistory(any(UserBalanceHistory.class));
    }

    @Test
    void 저장소가_null을_반환하면_IllegalStateException발생() {
        // given
        BalanceHistoryService balanceHistoryService = new BalanceHistoryService(balanceRepository);
        long userId = 1L;
        long amount = 5000L;
        HistoryType historyType = HistoryType.CHARGE;

        // BalanceHistoryRequest 생성
        BalanceHistoryRequest balanceHistoryRequest = new BalanceHistoryRequest(amount, historyType);

        // Mock 설정: saveUserBalanceHistory 호출 시 null 반환
        given(balanceRepository.saveUserBalanceHistory(any(UserBalanceHistory.class))).willReturn(null);

        // when & then
        assertThatThrownBy(() -> balanceHistoryService.saveBalanceHistory(userId, balanceHistoryRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("잔액 히스토리 저장 실패: 저장 결과가 올바르지 않습니다.");

        // saveUserBalanceHistory 메서드 호출 여부 검증
        verify(balanceRepository).saveUserBalanceHistory(any(UserBalanceHistory.class));
    }
}