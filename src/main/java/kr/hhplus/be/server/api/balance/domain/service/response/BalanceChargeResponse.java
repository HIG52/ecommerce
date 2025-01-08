package kr.hhplus.be.server.api.balance.domain.service.response;


public record BalanceChargeResponse(
        long userId,
        long balance
) { }