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
@Tag(name = "Balance API", description = "잔액 관리 API")
public class BalanceController {

    private final BalanceService balanceService;
    private final BalanceUsecase balanceUsecase;

    @Operation(
            summary = "잔액 조회",
            description = "사용자의 잔액을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "잔액 조회 성공",
                            content = @Content(schema = @Schema(implementation = BalanceResponseDTO.class)))
            }
    )
    @GetMapping("/api/balances/{userId}/balance")
    public ResponseEntity<BalanceResponseDTO> getUserPoint(
            @Valid @PathVariable(name = "userId") int userId) {

        BalanceResponse balanceResponse = balanceService.getUserBalance(userId);
        BalanceResponseDTO balanceResponseDTO =
                new BalanceResponseDTO(balanceResponse.balance(), balanceResponse.userId());

        return ResponseEntity.status(HttpStatus.OK).body(balanceResponseDTO);
    }

    @Operation(
            summary = "잔액 충전",
            description = "사용자의 잔액을 충전합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "잔액 충전 성공",
                            content = @Content(schema = @Schema(implementation = BalanceResponseDTO.class)))
            }
    )
    @PostMapping("/api/balances/{userId}/charge")
    public ResponseEntity<BalanceResponseDTO> userPointCharge(
            @Valid @PathVariable(name = "userId") int userId,
            @Valid @RequestBody BalanceRequestDTO balanceRequestDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(balanceUsecase.chargeUserBalance(userId, balanceRequestDTO));
    }

}