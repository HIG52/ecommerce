package kr.hhplus.be.server.balance.domain.service.response;


public record BalanceInfo(
        long userId,
        long balance
) { }