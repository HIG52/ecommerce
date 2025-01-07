package kr.hhplus.be.server.api.balance.presentation.controller;

import kr.hhplus.be.server.api.balance.presentation.dto.BalanceRequestDTO;
import kr.hhplus.be.server.api.balance.presentation.dto.BalanceResponseDTO;
import kr.hhplus.be.server.api.balance.domain.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/api/balances/{userId}/balance")
    public ResponseEntity<BalanceResponseDTO> getUserPoint(
            @PathVariable(name = "userId") int userId) {

        BalanceResponseDTO responseDTO = balanceService.getUserBalance(userId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/api/balances/{userId}/charge")
    public ResponseEntity<BalanceResponseDTO> userPointCharge(
            @PathVariable(name = "userId") int userId,
            @RequestBody BalanceRequestDTO balanceRequestDTO) {

        balanceRequestDTO.setUserId(userId);
        BalanceResponseDTO responseDTO = balanceService.chargeUserBalance(balanceRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}