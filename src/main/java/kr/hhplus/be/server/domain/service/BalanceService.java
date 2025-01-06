package kr.hhplus.be.server.domain.service;

import kr.hhplus.be.server.api.dto.BalanceResponseDTO;
import kr.hhplus.be.server.api.usecase.BalanceUsecase;
import kr.hhplus.be.server.domain.entity.User;
import kr.hhplus.be.server.domain.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService implements BalanceUsecase {

    private final BalanceRepository balanceRepository;

    @Override
    public BalanceResponseDTO getUserBalance(long userId) {

        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO();
        User user = balanceRepository.getUser(userId);
        balanceResponseDTO.setBalance(user.getBalance());
        balanceResponseDTO.setUserId(user.getUserId());

        return balanceResponseDTO;
    }

    @Override
    public BalanceResponseDTO chargeUserBalance(long userId, long amount) {

        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO();
        User user = balanceRepository.getUser(userId);
        user.addBalance(amount);
        balanceRepository.saveUser(user);

        balanceResponseDTO.setBalance(user.getBalance());
        balanceResponseDTO.setUserId(user.getUserId());

        return balanceResponseDTO;
    }


}
