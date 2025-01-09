package kr.hhplus.be.server.api.balance.presentation.dto;

public record BalanceRequestDTO (
        long userId,
        long amount
){
}
