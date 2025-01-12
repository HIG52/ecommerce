package kr.hhplus.be.server.balance.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.balance.domain.service.response.BalanceResponse;
import kr.hhplus.be.server.balance.presentation.dto.BalanceRequestDTO;
import kr.hhplus.be.server.balance.presentation.dto.BalanceResponseDTO;
import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.presentation.usecase.BalanceUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BalanceController implements BalanceControllerDocs {

    private final BalanceService balanceService;
    private final BalanceUsecase balanceUsecase;

    @GetMapping("/api/balances/{userId}/balance")
    public ResponseEntity<BalanceResponseDTO> getUserBalance(
            @Valid @PathVariable(name = "userId") long userId) {

        BalanceResponse balanceResponse = balanceService.getUserBalance(userId);
        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO(balanceResponse.balance(), balanceResponse.userId());

        return ResponseEntity.status(HttpStatus.OK).body(balanceResponseDTO);
    }


    @PostMapping("/api/balances/{userId}/charge")
    public ResponseEntity<BalanceResponseDTO> userPointCharge(
            @Valid @PathVariable(name = "userId") long userId,
            @Valid @RequestBody BalanceRequestDTO balanceRequestDTO) {

        BalanceResponseDTO balanceResponseDTO = balanceUsecase.chargeUserBalance(userId, balanceRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(balanceResponseDTO);
    }

}