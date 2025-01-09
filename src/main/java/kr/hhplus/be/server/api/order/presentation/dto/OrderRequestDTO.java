package kr.hhplus.be.server.api.order.presentation.dto;

import java.util.List;

public record OrderRequestDTO(
        Long userId,
        Long orderId,
        List<Long> productIds,
        List<Long> productQuantities,
        List<Long> productPrices
) {

}
