package kr.hhplus.be.server.balance.domain.service;

import kr.hhplus.be.server.balance.domain.service.request.BalanceDecreaseRequest;
import kr.hhplus.be.server.balance.domain.service.request.BalanceRequest;
import kr.hhplus.be.server.balance.domain.service.response.BalanceChargeInfo;
import kr.hhplus.be.server.balance.domain.service.response.BalanceInfo;
import kr.hhplus.be.server.balance.domain.entity.User;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceInfo getUserBalance(long userId) {

        User user = balanceRepository.getUser(userId);

        if(user == null) {
            throw new IllegalArgumentException("사용자 정보가 존재하지 않습니다.");
        }

        return new BalanceInfo(user.getUserId(), user.getBalance());
    }

    @Transactional
    public BalanceChargeInfo chargeUserBalance(long userId, BalanceRequest balanceRequest) {

        User user = balanceRepository.getUser(userId);
        user.addBalance(balanceRequest.amount());
        User resultUser = balanceRepository.saveUser(user);

        if (resultUser == null) {
            throw new IllegalStateException("잔액 충전 실패: 충전 결과가 올바르지 않습니다.");
        }

        return new BalanceChargeInfo(resultUser.getUserId(), resultUser.getBalance());
    }

    @Transactional
    public BalanceInfo decreaseBalance(BalanceDecreaseRequest balanceDecreaseRequest) {
        User user = balanceRepository.getUserWithLock(balanceDecreaseRequest.userId());

        if (user == null) {
            throw new IllegalArgumentException("사용자 정보가 존재하지 않습니다.");
        }

        user.decreaseBalance(balanceDecreaseRequest.amount());

        if(user.getBalance() < 0) {
            throw new IllegalStateException("잔액 차감 실패: 잔액이 부족합니다.");
        }

        User resultUser = balanceRepository.saveUser(user);

        if (resultUser == null) {
            throw new IllegalStateException("잔액 차감 실패: 차감 결과가 올바르지 않습니다.");
        }

        return new BalanceInfo(resultUser.getUserId(), resultUser.getBalance());
    }

}
