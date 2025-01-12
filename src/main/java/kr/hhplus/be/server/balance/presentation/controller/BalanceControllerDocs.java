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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Balance API", description = "잔액 관리 API")
public interface BalanceControllerDocs {

    @Operation(
            summary = "잔액 조회",
            description = "사용자의 잔액을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "잔액 조회 성공",
                            content = @Content(schema = @Schema(implementation = BalanceResponseDTO.class)))
            }
    )
    ResponseEntity<BalanceResponseDTO> getUserBalance(@Valid @PathVariable(name = "userId") long userId);

    @Operation(
            summary = "잔액 충전",
            description = "사용자의 잔액을 충전합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "잔액 충전 성공",
                            content = @Content(schema = @Schema(implementation = BalanceResponseDTO.class)))
            }
    )
    ResponseEntity<BalanceResponseDTO> userPointCharge(
            @Valid @PathVariable(name = "userId") long userId,
            @Valid @RequestBody BalanceRequestDTO balanceRequestDTO);
}
