package kr.hhplus.be.server.api.balance.presentation.usecase;

import kr.hhplus.be.server.api.balance.domain.service.BalanceHistoryService;
import kr.hhplus.be.server.api.balance.domain.service.BalanceService;
import kr.hhplus.be.server.api.balance.domain.service.request.BalanceHistoryRequest;
import kr.hhplus.be.server.api.balance.domain.service.request.BalanceRequest;
import kr.hhplus.be.server.api.balance.domain.service.response.BalanceChargeResponse;
import kr.hhplus.be.server.api.balance.domain.service.response.BalanceHistoryResponse;
import kr.hhplus.be.server.api.balance.presentation.dto.BalanceRequestDTO;
import kr.hhplus.be.server.api.balance.presentation.dto.BalanceResponseDTO;
import kr.hhplus.be.server.common.type.HistoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BalanceUsecase {
    private final BalanceService balanceService;
    private final BalanceHistoryService balanceHistoryService;

    @Transactional
    public BalanceResponseDTO chargeUserBalance(long userId, BalanceRequestDTO balanceRequestDTO) {

        try{
            BalanceRequest balanceRequest = new BalanceRequest(balanceRequestDTO.amount());
            BalanceHistoryRequest balanceHistoryRequest = new BalanceHistoryRequest(balanceRequestDTO.amount(), HistoryType.CHARGE);

            BalanceChargeResponse balanceChargeResponse = balanceService.chargeUserBalance(userId, balanceRequest);
            BalanceHistoryResponse balanceHistoryResponse = balanceHistoryService.saveBalanceHistory(userId, balanceHistoryRequest);

            if (balanceChargeResponse == null) {
                throw new IllegalStateException("잔액 충전 실패: 충전 결과가 올바르지 않습니다.");
            }

            if (balanceHistoryResponse == null) {
                throw new IllegalStateException("잔액 히스토리 저장 실패: 저장 결과가 올바르지 않습니다.");
            }

            return new BalanceResponseDTO(balanceChargeResponse.userId(), balanceChargeResponse.balance());
        }catch (Exception e){
            throw  new RuntimeException("잔액 충전 처리중 문제가 발생하였습니다.");
        }

    }

}
