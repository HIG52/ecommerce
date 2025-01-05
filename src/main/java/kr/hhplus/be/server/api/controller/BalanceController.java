package kr.hhplus.be.server.api.controller;

import kr.hhplus.be.server.api.dto.BalanceRequestDTO;
import kr.hhplus.be.server.api.dto.BalanceResponseDTO;
import kr.hhplus.be.server.api.usecase.BalanceUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BalanceController {

    private final BalanceUsecase balanceUsecase;

    @Autowired
    public BalanceController(BalanceUsecase balanceUsecase) {
        this.balanceUsecase = balanceUsecase;
    }

    @GetMapping("/api/balances/{userId}/balance")
    public ResponseEntity<BalanceResponseDTO> getUserPoint(
            @PathVariable(name = "userId") int userId) {

        BalanceResponseDTO response = new BalanceResponseDTO();

        response.setUserId(userId);
        response.setBalance(balanceUsecase.getUserBalance(userId));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/api/balances/{userId}/charge")
    public ResponseEntity<BalanceResponseDTO> userPointCharge(
            @PathVariable(name = "userId") int userId,
            @RequestBody BalanceRequestDTO balanceRequestDTO) {

        BalanceResponseDTO response = new BalanceResponseDTO();

        response.setUserId(userId);
        response.setBalance(20000);

        if(userId <= 0) {
            response.setMessage("사용자가 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}