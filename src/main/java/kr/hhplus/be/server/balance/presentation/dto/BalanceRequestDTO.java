package kr.hhplus.be.server.balance.presentation.dto;

public record BalanceRequestDTO (
        long userId,
        long amount
){
}
