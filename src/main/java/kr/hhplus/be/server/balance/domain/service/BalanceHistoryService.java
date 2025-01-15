package kr.hhplus.be.server.balance.domain.service;

import kr.hhplus.be.server.balance.domain.entity.UserBalanceHistory;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.balance.domain.service.request.BalanceHistoryRequest;
import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceHistoryService {

    private final BalanceRepository balanceRepository;

    @Transactional
    public void saveBalanceHistory(long userId, BalanceHistoryRequest balanceHistoryRequest) {
        try{
            UserBalanceHistory userBalanceHistory = UserBalanceHistory.createUserBalanceHistory(userId, balanceHistoryRequest.historyType(), balanceHistoryRequest.amount());

            UserBalanceHistory resultUserBalanceHistory = balanceRepository.saveUserBalanceHistory(userBalanceHistory);
        }catch (Exception e){
            throw new CustomExceptionHandler(ErrorCode.BALANCE_HISTORY_SAVE_FAILED, e);
        }
    }

}
