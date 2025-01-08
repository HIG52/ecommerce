package kr.hhplus.be.server.api.balance.domain.service;

import kr.hhplus.be.server.api.balance.domain.service.request.BalanceRequest;
import kr.hhplus.be.server.api.balance.domain.service.response.BalanceChargeResponse;
import kr.hhplus.be.server.api.balance.domain.service.response.BalanceResponse;
import kr.hhplus.be.server.api.balance.domain.entity.User;
import kr.hhplus.be.server.api.balance.domain.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceResponse getUserBalance(long userId) {

        User user = balanceRepository.getUser(userId);

        if(user == null) {
            throw new IllegalArgumentException("사용자 정보가 존재하지 않습니다.");
        }

        return new BalanceResponse(user.getUserId(), user.getBalance());
    }

    @Transactional
    public BalanceChargeResponse chargeUserBalance(long userId, BalanceRequest balanceRequest) {

        User user = balanceRepository.getUser(userId);
        user.addBalance(balanceRequest.amount());
        User resultUser = balanceRepository.saveUser(user);

        return new BalanceChargeResponse(resultUser.getUserId(), resultUser.getBalance());
    }

}
