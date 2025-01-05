package kr.hhplus.be.server.domain.service;

import kr.hhplus.be.server.api.usecase.BalanceUsecase;
import kr.hhplus.be.server.domain.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService implements BalanceUsecase {

    private final BalanceRepository balanceRepository;

    @Autowired
    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }


    @Override
    public long getUserBalance(long userId) {

        return balanceRepository.getUserBalance(userId);
    }

}
