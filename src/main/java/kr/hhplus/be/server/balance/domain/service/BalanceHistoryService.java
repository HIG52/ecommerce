package kr.hhplus.be.server.balance.domain.service;

import kr.hhplus.be.server.balance.domain.entity.UserBalanceHistory;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.request.BalanceHistoryRequest;
import kr.hhplus.be.server.balance.domain.service.response.BalanceHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceHistoryService {

    private final BalanceRepository balanceRepository;

    @Transactional
    public void saveBalanceHistory(long userId, BalanceHistoryRequest balanceHistoryRequest) {

        UserBalanceHistory userBalanceHistory = UserBalanceHistory.createUserBalanceHistory(userId, balanceHistoryRequest.historyType(), balanceHistoryRequest.amount());

        UserBalanceHistory resultUserBalanceHistory = balanceRepository.saveUserBalanceHistory(userBalanceHistory);

        if (resultUserBalanceHistory == null) {
            throw new IllegalStateException("잔액 히스토리 저장 실패: 저장 결과가 올바르지 않습니다.");
        }

    }

}
