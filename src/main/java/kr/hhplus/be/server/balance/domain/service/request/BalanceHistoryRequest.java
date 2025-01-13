package kr.hhplus.be.server.balance.domain.service.request;

import kr.hhplus.be.server.common.type.HistoryType;

public record BalanceHistoryRequest(
        long amount,
        HistoryType historyType
) {
}
