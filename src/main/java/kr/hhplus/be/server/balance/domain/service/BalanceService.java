package kr.hhplus.be.server.balance.domain.service;

import kr.hhplus.be.server.balance.domain.service.request.BalanceDecreaseRequest;
import kr.hhplus.be.server.balance.domain.service.request.BalanceRequest;
import kr.hhplus.be.server.balance.domain.service.info.BalanceChargeInfo;
import kr.hhplus.be.server.balance.domain.service.info.BalanceInfo;
import kr.hhplus.be.server.balance.domain.entity.User;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
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
            throw new CustomExceptionHandler(ErrorCode.USER_NOT_FOUND);
        }

        return new BalanceInfo(user.getUserId(), user.getBalance());
    }

    @Transactional
    public BalanceChargeInfo chargeUserBalance(long userId, BalanceRequest balanceRequest) {

        User user = balanceRepository.getUserWithLock(userId);

        if(user == null) {
            throw new CustomExceptionHandler(ErrorCode.USER_NOT_FOUND);
        }

        user.addBalance(balanceRequest.amount());

        User resultUser = balanceRepository.saveUser(user);

        if (resultUser == null) {
            throw new CustomExceptionHandler(ErrorCode.BALANCE_CHARGE_FAILED);
        }

        return new BalanceChargeInfo(resultUser.getUserId(), resultUser.getBalance());
    }

    @Transactional
    public BalanceInfo decreaseBalance(BalanceDecreaseRequest balanceDecreaseRequest) {
        try {
            User user = balanceRepository.getUser(balanceDecreaseRequest.userId());

            if (user == null) {
                throw new CustomExceptionHandler(ErrorCode.USER_NOT_FOUND);
            }

            user.decreaseBalance(balanceDecreaseRequest.amount());

            User resultUser = balanceRepository.saveUser(user);

            return new BalanceInfo(resultUser.getUserId(), resultUser.getBalance());
        }catch (CustomExceptionHandler e) {
            throw e;
        }catch (Exception e) {
            throw new CustomExceptionHandler(ErrorCode.BALANCE_USE_FAILED, e);
        }
    }

}
