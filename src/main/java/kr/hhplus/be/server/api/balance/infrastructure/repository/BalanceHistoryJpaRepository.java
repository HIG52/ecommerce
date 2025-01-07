package kr.hhplus.be.server.api.balance.infrastructure.repository;

import kr.hhplus.be.server.api.balance.domain.entity.UserBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceHistoryJpaRepository extends JpaRepository<UserBalanceHistory, Long> {

}
