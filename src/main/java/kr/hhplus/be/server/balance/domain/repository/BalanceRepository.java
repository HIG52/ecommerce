package kr.hhplus.be.server.balance.domain.repository;

import kr.hhplus.be.server.balance.domain.entity.User;
import kr.hhplus.be.server.balance.domain.entity.UserBalanceHistory;

public interface BalanceRepository {

    User getUser(long userId);

    User saveUser(User user);

    UserBalanceHistory saveUserBalanceHistory(UserBalanceHistory userBalanceHistory);

    User getUserWithLock(long l);
}
