package kr.hhplus.be.server.api.balance.domain.service.response;


public record BalanceResponse(
        long userId,
        long balance
) { }