package kr.hhplus.be.server.domain.repository;

import kr.hhplus.be.server.domain.entity.User;

public interface BalanceRepository {

    User getUser(long userId);

}
