package kr.hhplus.be.server.balance.presentation.usecase;

import kr.hhplus.be.server.balance.domain.service.BalanceHistoryService;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.domain.service.request.BalanceHistoryRequest;
import kr.hhplus.be.server.balance.domain.service.request.BalanceRequest;
import kr.hhplus.be.server.balance.domain.service.info.BalanceChargeInfo;
import kr.hhplus.be.server.balance.presentation.dto.BalanceChargeResponseDTO;
import kr.hhplus.be.server.balance.presentation.dto.BalanceChargeRequestDTO;
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
    public BalanceChargeResponseDTO chargeUserBalance(long userId, BalanceChargeRequestDTO balanceChargeRequestDTO) {

        try{
            BalanceRequest balanceRequest = new BalanceRequest(balanceChargeRequestDTO.amount());
            BalanceHistoryRequest balanceHistoryRequest = new BalanceHistoryRequest(balanceChargeRequestDTO.amount(), HistoryType.CHARGE);

            BalanceChargeInfo balanceChargeInfo = balanceService.chargeUserBalance(userId, balanceRequest);
            balanceHistoryService.saveBalanceHistory(userId, balanceHistoryRequest);

            return new BalanceChargeResponseDTO(balanceChargeInfo.userId(), balanceChargeInfo.balance());
        }catch (Exception e){
            throw  new RuntimeException("잔액 충전 처리중 문제가 발생하였습니다.");
        }

    }

}
