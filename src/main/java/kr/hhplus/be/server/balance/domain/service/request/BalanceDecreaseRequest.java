package kr.hhplus.be.server.balance.domain.service.request;

public record BalanceDecreaseRequest(
        long userId,
        long amount
) {
}
