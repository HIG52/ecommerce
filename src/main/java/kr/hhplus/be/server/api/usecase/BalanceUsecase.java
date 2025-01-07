package kr.hhplus.be.server.api.usecase;

import kr.hhplus.be.server.api.dto.BalanceResponseDTO;

public interface BalanceUsecase {

    BalanceResponseDTO getUserBalance(long userId);


}
