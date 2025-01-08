package kr.hhplus.be.server.api.balance.domain.service.response;


public record BalanceHistoryResponse(
        long userId,
        long amount
) { }