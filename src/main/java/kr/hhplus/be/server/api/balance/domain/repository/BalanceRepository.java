package kr.hhplus.be.server.api.balance.domain.repository;

import kr.hhplus.be.server.api.balance.domain.entity.User;

public interface BalanceRepository {

    User getUser(long userId);

    void saveUser(User user);
}
