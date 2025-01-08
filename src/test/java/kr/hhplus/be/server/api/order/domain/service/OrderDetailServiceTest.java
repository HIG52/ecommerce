package kr.hhplus.be.server.api.order.domain.service;

import kr.hhplus.be.server.api.order.domain.entity.OrderDetail;
import kr.hhplus.be.server.api.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.api.order.presentation.dto.OrderResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderDetailServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Test
    void 주문_상세정보를_저장하면_OrderResponseDTO_반환() {
        // given
        OrderDetailService orderDetailService = new OrderDetailService(orderRepository);

        Long orderId = 1L;
        List<Long> productIds = List.of(101L, 102L, 103L);
        List<Integer> productQuantities = List.of(2, 3, 1);
        List<Long> productPrices = List.of(2000L, 3000L, 1000L);

        List<OrderDetail> mockOrderDetails = List.of(
                OrderDetail.createOrderDetail(orderId, 101L, 2, 2000L),
                OrderDetail.createOrderDetail(orderId, 102L, 3, 3000L),
                OrderDetail.createOrderDetail(orderId, 103L, 1, 1000L)
        );

        // Stubbing 없이 메서드 실행 후 캡처
        // when
         orderDetailService.createOrderDetails(orderId, productIds, productQuantities, productPrices);

        // then
        ArgumentCaptor<List<OrderDetail>> captor = ArgumentCaptor.forClass(List.class);
        verify(orderRepository).orderDetailsaveAll(captor.capture());
        List<OrderDetail> capturedOrderDetails = captor.getValue();

        assertThat(capturedOrderDetails).hasSize(3);
        assertThat(capturedOrderDetails.get(0).getProductId()).isEqualTo(101L);
        assertThat(capturedOrderDetails.get(0).getOrderQuantity()).isEqualTo(2);
        assertThat(capturedOrderDetails.get(0).getOrderAmount()).isEqualTo(2000L);
    }

}