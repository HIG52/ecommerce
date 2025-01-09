package kr.hhplus.be.server.api.order.presentation.usecase;

import kr.hhplus.be.server.api.order.domain.service.OrderDetailService;
import kr.hhplus.be.server.api.order.domain.service.OrderService;
import kr.hhplus.be.server.api.order.domain.service.request.OrderDetailsCreateRequest;
import kr.hhplus.be.server.api.order.domain.service.response.OrderResponse;
import kr.hhplus.be.server.api.order.presentation.dto.OrderRequestDTO;
import kr.hhplus.be.server.api.order.presentation.dto.OrderResponseDTO;
import kr.hhplus.be.server.api.product.domain.service.ProductService;
import kr.hhplus.be.server.api.product.domain.service.request.QuantityRequest;
import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderUsecase {
    private final OrderService orderService;
    private final ProductService productService;
    private final OrderDetailService orderDetailService;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        // 상품 재고 차감 및 락설정
        long totalQuantity = 0;
        long totalPrice = 0;
        for (int i = 0; i < orderRequestDTO.productIds().size(); i++) {
            QuantityRequest quantityRequest = new QuantityRequest(orderRequestDTO.productIds().get(i), orderRequestDTO.productQuantities().get(i));
            totalQuantity += orderRequestDTO.productQuantities().get(i);
            totalPrice += orderRequestDTO.productPrices().get(i) * orderRequestDTO.productQuantities().get(i);
            productService.decreaseProductQuantity(quantityRequest);
        }

        OrderResponse orderResponse = orderService.createOrder(orderRequestDTO.userId(), orderRequestDTO.orderTotalAmount());

        OrderDetailsCreateRequest orderDetailsCreateRequest = new OrderDetailsCreateRequest(orderResponse.orderId(), orderRequestDTO.productIds(), orderRequestDTO.productQuantities(), orderRequestDTO.productPrices());
        orderDetailService.createOrderDetails(orderDetailsCreateRequest);

        return new OrderResponseDTO(
                orderResponse.orderId(),
                orderResponse.userId(),
                orderResponse.orderTotalPrice(),
                PaymentStatusType.PENDING,
                OrderStatusType.ORDERED,
                totalQuantity,
                totalPrice);
    }
}
