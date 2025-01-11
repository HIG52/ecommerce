package kr.hhplus.be.server.balance.domain.service.response;


public record BalanceHistoryResponse(
        long userId,
        long amount
) { }