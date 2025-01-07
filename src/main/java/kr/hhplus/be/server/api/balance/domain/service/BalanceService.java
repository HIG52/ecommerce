package kr.hhplus.be.server.api.balance.domain.service;

import kr.hhplus.be.server.api.balance.presentation.dto.BalanceRequestDTO;
import kr.hhplus.be.server.api.balance.presentation.dto.BalanceResponseDTO;
import kr.hhplus.be.server.api.balance.domain.entity.User;
import kr.hhplus.be.server.api.balance.domain.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceResponseDTO getUserBalance(long userId) {

        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO();
        User user = balanceRepository.getUser(userId);
        balanceResponseDTO.setBalance(user.getBalance());
        balanceResponseDTO.setUserId(user.getUserId());

        return balanceResponseDTO;
    }

    public BalanceResponseDTO chargeUserBalance(BalanceRequestDTO balanceRequestDTO) {

        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO();
        User user = balanceRepository.getUser(balanceRequestDTO.getUserId());
        user.addBalance(balanceRequestDTO.getAmount());
        balanceRepository.saveUser(user);

        balanceResponseDTO.setBalance(user.getBalance());
        balanceResponseDTO.setUserId(user.getUserId());

        return balanceResponseDTO;
    }


}
