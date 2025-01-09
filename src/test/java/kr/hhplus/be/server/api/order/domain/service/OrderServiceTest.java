package kr.hhplus.be.server.api.order.domain.service;

import kr.hhplus.be.server.api.order.domain.entity.Order;
import kr.hhplus.be.server.api.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.api.order.domain.service.response.OrderPaymentStatusResponse;
import kr.hhplus.be.server.api.order.domain.service.response.OrderResponse;
import kr.hhplus.be.server.api.order.domain.service.response.OrderStatusResponse;
import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
        ReflectionTestUtils.setField(mockOrder, "orderId", 1L);
        given(orderRepository.save(any(Order.class))).willReturn(mockOrder);

        // when
        OrderResponse orderResponse = orderService.createOrder(userId, orderTotalAmount);

        // then
        assertThat(orderResponse.userId()).isEqualTo(userId);
        assertThat(orderResponse.orderTotalPrice()).isEqualTo(orderTotalAmount);
        assertThat(orderResponse.paymentStatus()).isEqualTo(PaymentStatusType.PENDING);
        assertThat(orderResponse.status()).isEqualTo(OrderStatusType.ORDERED);
    }

    @Test
    void 주문_상태_업데이트시_OrderStatusResponse_반환() {
        // given
        OrderService orderService = new OrderService(orderRepository);

        long orderId = 1L;
        OrderStatusType newStatus = OrderStatusType.CANCELLED;

        // 기존 주문 Mock 데이터 생성
        Order mockOrder = Order.createOrder(1L, 5000L, PaymentStatusType.PENDING, OrderStatusType.ORDERED);
        ReflectionTestUtils.setField(mockOrder, "orderId", orderId);

        // Mock 설정
        given(orderRepository.findByOrderId(orderId)).willReturn(mockOrder);

        // 상태 업데이트된 주문 Mock 데이터 생성
        Order updatedOrder = Order.createOrder(1L, 5000L, PaymentStatusType.PENDING, newStatus);
        ReflectionTestUtils.setField(updatedOrder, "orderId", orderId);

        given(orderRepository.save(mockOrder)).willReturn(updatedOrder);

        // when
        OrderStatusResponse response = orderService.updateOrderStatus(orderId, newStatus);

        // then
        assertThat(response.orderId()).isEqualTo(orderId);
        assertThat(response.orderStatus()).isEqualTo(newStatus);
    }

    @Test
    void 주문_결제_상태_업데이트시_OrderPaymentStatusResponse_반환() {
        // given
        OrderService orderService = new OrderService(orderRepository);

        long orderId = 1L;
        PaymentStatusType newPaymentStatus = PaymentStatusType.SUCCESS;

        // 기존 주문 Mock 데이터 생성
        Order mockOrder = Order.createOrder(1L, 5000L, PaymentStatusType.PENDING, OrderStatusType.ORDERED);
        ReflectionTestUtils.setField(mockOrder, "orderId", orderId);

        // Mock 설정: findByOrderId 호출 시 기존 Mock 데이터 반환
        given(orderRepository.findByOrderId(orderId)).willReturn(mockOrder);

        // Mock 설정: save 호출 시 업데이트된 주문 데이터 반환
        Order updatedOrder = Order.createOrder(1L, 5000L, newPaymentStatus, OrderStatusType.ORDERED);
        ReflectionTestUtils.setField(updatedOrder, "orderId", orderId);

        given(orderRepository.save(mockOrder)).willReturn(updatedOrder);

        // when
        OrderPaymentStatusResponse response = orderService.updateOrderPaymentStatus(orderId, newPaymentStatus);

        // then
        assertThat(response.orderId()).isEqualTo(orderId);
        assertThat(response.paymentStatusType()).isEqualTo(newPaymentStatus);

        // verify 호출 확인
        verify(orderRepository).findByOrderId(orderId);
        verify(orderRepository).save(mockOrder);
    }


}