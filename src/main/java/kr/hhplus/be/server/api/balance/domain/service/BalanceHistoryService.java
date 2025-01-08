package kr.hhplus.be.server.api.balance.domain.service;

import kr.hhplus.be.server.api.balance.domain.entity.UserBalanceHistory;
import kr.hhplus.be.server.api.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.api.balance.domain.service.request.BalanceHistoryRequest;
import kr.hhplus.be.server.api.balance.domain.service.response.BalanceHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceHistoryService {

    private final BalanceRepository balanceRepository;

    @Transactional
    public BalanceHistoryResponse saveBalanceHistory(long userId, BalanceHistoryRequest balanceHistoryRequest) {

        UserBalanceHistory userBalanceHistory = UserBalanceHistory.createUserBalanceHistory(userId, balanceHistoryRequest.historyType(), balanceHistoryRequest.amount());

        UserBalanceHistory resultUserBalanceHistory = balanceRepository.saveUserBalanceHistory(userBalanceHistory);

        return new BalanceHistoryResponse(resultUserBalanceHistory.getUserId(), userBalanceHistory.getAmount());
    }

}
