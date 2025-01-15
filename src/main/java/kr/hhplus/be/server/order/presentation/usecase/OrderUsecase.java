package kr.hhplus.be.server.order.presentation.usecase;

import kr.hhplus.be.server.order.domain.service.OrderDetailService;
import kr.hhplus.be.server.order.domain.service.OrderService;
import kr.hhplus.be.server.order.domain.service.request.OrderDetailsCreateRequest;
import kr.hhplus.be.server.order.domain.service.info.OrderInfo;
import kr.hhplus.be.server.order.presentation.dto.OrderRequestDTO;
import kr.hhplus.be.server.order.presentation.dto.OrderResponseDTO;
import kr.hhplus.be.server.product.domain.service.ProductService;
import kr.hhplus.be.server.product.domain.service.request.QuantityRequest;
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
        int totalQuantity = 0;
        long totalPrice = 0;
        for (int i = 0; i < orderRequestDTO.productIds().size(); i++) {
            QuantityRequest quantityRequest = new QuantityRequest(orderRequestDTO.productIds().get(i), orderRequestDTO.productQuantities().get(i));
            totalQuantity += orderRequestDTO.productQuantities().get(i);
            totalPrice += orderRequestDTO.productPrices().get(i) * Long.valueOf(orderRequestDTO.productQuantities().get(i));
            productService.decreaseProductQuantity(quantityRequest);
        }

        OrderInfo orderInfo = orderService.createOrder(orderRequestDTO.userId(), orderRequestDTO.orderTotalAmount());

        OrderDetailsCreateRequest orderDetailsCreateRequest = new OrderDetailsCreateRequest(orderInfo.orderId(), orderRequestDTO.productIds(), orderRequestDTO.productQuantities(), orderRequestDTO.productPrices());
        orderDetailService.createOrderDetails(orderDetailsCreateRequest);

        return new OrderResponseDTO(
                orderInfo.orderId(),
                orderInfo.userId(),
                orderInfo.orderTotalPrice(),
                PaymentStatusType.PENDING,
                OrderStatusType.ORDERED,
                totalQuantity,
                totalPrice);
    }
}
