package kr.hhplus.be.server.api.balance.infrastructure.repository;

import kr.hhplus.be.server.api.balance.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceJpaRepository extends JpaRepository<User, Long> {

    User findByUserId(long userId);
}
