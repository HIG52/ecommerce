package kr.hhplus.be.server.api.balance.domain.service;

import kr.hhplus.be.server.api.balance.domain.entity.UserBalanceHistory;
import kr.hhplus.be.server.api.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.common.type.HistoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceHistoryService {

    private final BalanceRepository balanceRepository;
    // TODO : 반환값 생성 예정
    public void saveChargeBalanceHistory(long userId, long amount) {

        UserBalanceHistory userBalanceHistory = UserBalanceHistory.createUserBalanceHistory(userId, HistoryType.CHARGE, amount);
        
        balanceRepository.saveUserBalanceHistory(userBalanceHistory);
    }

    public void saveUseBalanceHistory(long userId, long amount) {

        UserBalanceHistory userBalanceHistory = UserBalanceHistory.createUserBalanceHistory(userId, HistoryType.USE, amount);

        balanceRepository.saveUserBalanceHistory(userBalanceHistory);
    }

}
