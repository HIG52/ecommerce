package kr.hhplus.be.server.order.domain.service.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.order.domain.service.OrderService;
import kr.hhplus.be.server.order.domain.service.info.OrderPaymentStatusInfo;
import kr.hhplus.be.server.order.domain.service.info.OrderInfo;
import kr.hhplus.be.server.order.domain.service.info.OrderStatusInfo;
import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = Order.createOrder(1L, 5000L, PaymentStatusType.PENDING, OrderStatusType.ORDERED);
        ReflectionTestUtils.setField(order, "orderId", 1L);
    }

    @Test
    @DisplayName("사용자 ID와 총 금액을 입력하면 주문이 생성된다")
    void createOrder_Success() {
        // given
        given(orderRepository.save(order)).willReturn(order);

        // when
        OrderInfo response = orderService.createOrder(1L, 5000L);

        // then
        assertThat(response.orderId()).isEqualTo(1L);
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.orderTotalPrice()).isEqualTo(5000L);
        assertThat(response.paymentStatus()).isEqualTo(PaymentStatusType.PENDING);
        assertThat(response.status()).isEqualTo(OrderStatusType.ORDERED);

        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("주문 저장에 실패하면 CustomExceptionHandler를 반환한다")
    void createOrder_Fail() {
        // given
        given(orderRepository.save(order)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> orderService.createOrder(1L, 5000L))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.ORDER_NOT_CREATE.getMessage());
    }

    @Test
    @DisplayName("주문 ID와 상태를 입력하면 주문 상태가 업데이트된다")
    void updateOrderStatus_Success() {
        // given
        given(orderRepository.findByOrderId(1L)).willReturn(order);
        given(orderRepository.save(order)).willReturn(order);

        // when
        OrderStatusInfo response = orderService.updateOrderStatus(1L, OrderStatusType.PAYMENT_COMPLETED);

        // then
        assertThat(response.orderId()).isEqualTo(1L);
        assertThat(response.orderStatus()).isEqualTo(OrderStatusType.PAYMENT_COMPLETED);

        verify(orderRepository).findByOrderId(1L);
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("주문 상태 업데이트 실패 시 CustomExceptionHandler를 반환한다")
    void updateOrderStatus_Fail() {
        // given
        given(orderRepository.findByOrderId(1L)).willReturn(order);
        given(orderRepository.save(order)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> orderService.updateOrderStatus(1L, OrderStatusType.PAYMENT_COMPLETED))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.ORDER_STATUS_UPDATE_FAIL.getMessage());
    }

    @Test
    @DisplayName("주문 ID와 결제 상태를 입력하면 결제 상태가 업데이트된다")
    void updateOrderPaymentStatus_Success() {
        // given
        given(orderRepository.findByOrderId(1L)).willReturn(order);
        given(orderRepository.save(order)).willReturn(order);

        // when
        OrderPaymentStatusInfo response = orderService.updateOrderPaymentStatus(1L, PaymentStatusType.SUCCESS);

        // then
        assertThat(response.orderId()).isEqualTo(1L);
        assertThat(response.paymentStatusType()).isEqualTo(PaymentStatusType.SUCCESS);

        verify(orderRepository).findByOrderId(1L);
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("결제 상태 업데이트 실패 시 CustomExceptionHandler를 반환한다")
    void updateOrderPaymentStatus_Fail() {
        // given
        given(orderRepository.findByOrderId(1L)).willReturn(order);
        given(orderRepository.save(order)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> orderService.updateOrderPaymentStatus(1L, PaymentStatusType.SUCCESS))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.ORDER_PAYMENT_STATUS_UPDATE_FAIL.getMessage());
    }


}