package kr.hhplus.be.server.api.balance.presentation.dto;

import lombok.Data;

@Data
public class BalanceResponseDTO {
    private long userId;
    private long balance;
    private String message;
}