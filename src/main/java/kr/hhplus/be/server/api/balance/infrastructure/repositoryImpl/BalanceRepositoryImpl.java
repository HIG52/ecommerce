package kr.hhplus.be.server.api.balance.infrastructure.repositoryImpl;

import kr.hhplus.be.server.api.balance.domain.entity.User;
import kr.hhplus.be.server.api.balance.domain.entity.UserBalanceHistory;
import kr.hhplus.be.server.api.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.api.balance.infrastructure.repository.BalanceHistoryJpaRepository;
import kr.hhplus.be.server.api.balance.infrastructure.repository.BalanceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepository {

    private final BalanceJpaRepository balanceJpaRepository;
    private final BalanceHistoryJpaRepository balanceHistoryJpaRepository;

    @Override
    public User getUser(long userId) {
        return balanceJpaRepository.findByUserId(userId);
    }

    @Override
    public void saveUser(User user) {
        balanceJpaRepository.save(user);
    }

    @Override
    public void saveUserBalanceHistory(UserBalanceHistory userBalanceHistory) {
        balanceHistoryJpaRepository.save(userBalanceHistory);
    }

}
