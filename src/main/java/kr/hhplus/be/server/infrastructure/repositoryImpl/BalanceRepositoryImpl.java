package kr.hhplus.be.server.infrastructure.repositoryImpl;

import kr.hhplus.be.server.domain.entity.User;
import kr.hhplus.be.server.domain.repository.BalanceRepository;
import kr.hhplus.be.server.infrastructure.repository.BalanceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepository {

    private final BalanceJpaRepository balanceJpaRepository;

    @Override
    public User getUser(long userId) {
        return balanceJpaRepository.findByUserId(userId);
    }

    @Override
    public void saveUser(User user) {
        balanceJpaRepository.save(user);
    }

}
