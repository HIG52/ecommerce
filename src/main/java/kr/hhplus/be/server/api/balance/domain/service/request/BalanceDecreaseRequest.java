package kr.hhplus.be.server.api.balance.domain.service.request;

public record BalanceDecreaseRequest(
        long userId,
        long amount
) {
}
