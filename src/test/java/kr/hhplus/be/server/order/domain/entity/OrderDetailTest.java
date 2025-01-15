package kr.hhplus.be.server.order.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDetailTest {

    private OrderDetail orderDetail;

    @BeforeEach
    void setUp() {
        orderDetail = OrderDetail.createOrderDetail(1L, 2L, 5, 1000L);
        ReflectionTestUtils.setField(orderDetail, "orderDetailId", 1L);
    }

    @Test
    @DisplayName("OrderDetail 생성 후 필드 값이 올바르게 초기화된다")
    void createOrderDetail_Success() {
        // then
        assertThat(orderDetail.getOrderDetailId()).isEqualTo(1L);
        assertThat(orderDetail.getOrderId()).isEqualTo(1L);
        assertThat(orderDetail.getProductId()).isEqualTo(2L);
        assertThat(orderDetail.getOrderQuantity()).isEqualTo(5);
        assertThat(orderDetail.getOrderAmount()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("OrderDetail의 필드 값이 null이 아니고 올바르게 유지된다")
    void orderDetailFields_ShouldNotBeNull() {
        // then
        assertThat(orderDetail.getOrderId()).isNotNull();
        assertThat(orderDetail.getProductId()).isNotNull();
        assertThat(orderDetail.getOrderQuantity()).isNotNull();
        assertThat(orderDetail.getOrderAmount()).isNotNull();
    }

}