package kr.hhplus.be.server.balance.domain.service.response;


public record BalanceChargeResponse(
        long userId,
        long balance
) { }