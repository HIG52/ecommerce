package kr.hhplus.be.server.api.balance.infrastructure.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.api.balance.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface BalanceJpaRepository extends JpaRepository<User, Long> {

    User findByUserId(long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    User findByUserIdWithLock(long userId);
}
