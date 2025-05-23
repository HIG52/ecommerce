package kr.hhplus.be.server.order.presentation.dto;

import java.util.List;

public record OrderRequestDTO(
        Long userId,
        Long orderTotalAmount,
        List<Long> productIds,
        List<Integer> productQuantities,
        List<Long> productPrices
) {

}
