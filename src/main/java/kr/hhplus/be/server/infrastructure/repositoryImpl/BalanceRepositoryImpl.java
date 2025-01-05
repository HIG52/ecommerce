package kr.hhplus.be.server.infrastructure.repositoryImpl;

import kr.hhplus.be.server.domain.repository.BalanceRepository;
import kr.hhplus.be.server.infrastructure.repository.BalanceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepository {

    private final BalanceJpaRepository balanceJpaRepository;

    @Override
    public long getUserBalance(long userId) {

        return balanceJpaRepository.findByUserId(userId).getBalance();
    }

}
