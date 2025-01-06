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

        BalanceResponseDTO response = new BalanceResponseDTO();
        User user = balanceRepository.getUser(userId);
        response.setBalance(user.getBalance());
        response.setUserId(user.getUserId());

        return response;
    }


}
