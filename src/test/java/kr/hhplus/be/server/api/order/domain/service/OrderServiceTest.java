package kr.hhplus.be.server.api.order.domain.service;

import kr.hhplus.be.server.api.order.domain.entity.Order;
import kr.hhplus.be.server.api.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.api.order.presentation.dto.OrderResponseDTO;
import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Test
    void 주문_생성하면_OrderResponseDTO_반환() {
        // given
        OrderService orderService = new OrderService(orderRepository);

        long userId = 1L;
        long orderTotalAmount = 5000L;

        Order mockOrder = Order.createOrder(userId, orderTotalAmount, PaymentStatusType.PENDING, OrderStatusType.ORDERED);
        given(orderRepository.save(any(Order.class))).willReturn(mockOrder);

        // when
        OrderResponseDTO orderResponseDTO = orderService.createOrder(userId, orderTotalAmount);

        // then
        assertThat(orderResponseDTO.getUserId()).isEqualTo(userId);
        assertThat(orderResponseDTO.getTotalPrice()).isEqualTo(orderTotalAmount);
        assertThat(orderResponseDTO.getPaymentStatus()).isEqualTo(PaymentStatusType.PENDING);
        assertThat(orderResponseDTO.getStatus()).isEqualTo(OrderStatusType.ORDERED);
    }

    @Test
    void 주문상태를_업데이트하면_변경된_OrderResponseDTO_반환() {
        // given
        OrderService orderService = new OrderService(orderRepository);
        long orderId = 1L;
        OrderStatusType newStatus = OrderStatusType.CANCELLED;

        Order mockOrder = Order.createOrder(1L, 5000L, PaymentStatusType.SUCCESS, OrderStatusType.ORDERED);

        given(orderRepository.findByOrderId(orderId)).willReturn(mockOrder);
        given(orderRepository.save(any(Order.class))).willAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.updateStatus(newStatus); // 상태 업데이트 적용
            return order;
        });

        // when
        OrderResponseDTO updatedOrder = orderService.updateOrderStatus(orderId, newStatus);

        // then
        assertThat(updatedOrder.getStatus()).isEqualTo(newStatus);
        assertThat(updatedOrder.getPaymentStatus()).isEqualTo(PaymentStatusType.SUCCESS);
        assertThat(updatedOrder.getTotalPrice()).isEqualTo(5000L);
    }


}