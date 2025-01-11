package kr.hhplus.be.server.balance.domain.service.response;


public record BalanceResponse(
        long userId,
        long balance
) { }