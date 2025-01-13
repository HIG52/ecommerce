package kr.hhplus.be.server.order.domain.entity;

import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("정상적으로 Order 객체를 생성한다.")
    void createOrder() {
        // given
        Long userId = 1L;
        Long orderTotalAmount = 5000L;
        PaymentStatusType paymentStatus = PaymentStatusType.PENDING;
        OrderStatusType status = OrderStatusType.ORDERED;

        // when
        Order order = Order.createOrder(userId, orderTotalAmount, paymentStatus, status);

        // then
        assertThat(order.getUserId()).isEqualTo(userId);
        assertThat(order.getOrderTotalAmount()).isEqualTo(orderTotalAmount);
        assertThat(order.getPaymentStatus()).isEqualTo(paymentStatus);
        assertThat(order.getStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("Order 상태를 업데이트한다.")
    void updateStatus() {
        // given
        Order order = Order.createOrder(1L, 5000L, PaymentStatusType.PENDING, OrderStatusType.ORDERED);

        // when
        order.updateStatus(OrderStatusType.PAYMENT_COMPLETED);

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatusType.PAYMENT_COMPLETED);
    }

    @Test
    @DisplayName("Order 결제 상태를 업데이트한다.")
    void updatePaymentStatus() {
        // given
        Order order = Order.createOrder(1L, 5000L, PaymentStatusType.PENDING, OrderStatusType.ORDERED);

        // when
        order.updatePaymentStatus(PaymentStatusType.SUCCESS);

        // then
        assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatusType.SUCCESS);
    }

}