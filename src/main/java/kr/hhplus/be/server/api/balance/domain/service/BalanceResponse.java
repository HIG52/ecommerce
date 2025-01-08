package kr.hhplus.be.server.api.balance.domain.service;


public record BalanceResponse(
        long balance,
        long userId
) { }