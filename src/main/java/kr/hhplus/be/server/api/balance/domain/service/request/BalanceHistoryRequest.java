package kr.hhplus.be.server.api.balance.domain.service.request;

import kr.hhplus.be.server.common.type.HistoryType;

public record BalanceHistoryRequest(
        long amount,
        HistoryType historyType
) {
}
