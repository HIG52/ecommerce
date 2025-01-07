package kr.hhplus.be.server.api.balance.presentation.dto;

import lombok.Data;

@Data
public class BalanceRequestDTO {
    private long userId;
    private long amount;

}
