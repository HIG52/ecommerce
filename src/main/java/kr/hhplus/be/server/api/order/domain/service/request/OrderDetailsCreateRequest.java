package kr.hhplus.be.server.api.order.domain.service.request;

import java.util.List;

public record OrderDetailsCreateRequest(
        Long orderId,
        List<Long> productIds,
        List<Integer> productQuantities,
        List<Long> productPrices
) {
}
