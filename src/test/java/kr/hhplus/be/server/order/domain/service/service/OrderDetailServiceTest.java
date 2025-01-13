package kr.hhplus.be.server.order.domain.service.service;

import kr.hhplus.be.server.order.domain.entity.OrderDetail;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.order.domain.service.OrderDetailService;
import kr.hhplus.be.server.order.domain.service.request.OrderDetailsCreateRequest;
import kr.hhplus.be.server.order.domain.service.response.OrderDetailsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
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

        // CreateOrderDetailsRequest 객체 생성
        OrderDetailsCreateRequest request = new OrderDetailsCreateRequest(
                1L,
                List.of(101L, 102L, 103L),
                List.of(2, 3, 1),
                List.of(2000L, 3000L, 1000L)
        );

        // Mock 데이터 생성
        List<OrderDetail> mockOrderDetails = List.of(
                OrderDetail.createOrderDetail(request.orderId(), 101L, 2, 2000L),
                OrderDetail.createOrderDetail(request.orderId(), 102L, 3, 3000L),
                OrderDetail.createOrderDetail(request.orderId(), 103L, 1, 1000L)
        );

        // Stubbing: orderDetailsaveAll 호출 시 Mock 데이터 반환
        given(orderRepository.orderDetailsaveAll(anyList())).willReturn(mockOrderDetails);

        // when
        List<OrderDetailsResponse> responses = orderDetailService.createOrderDetails(request);

        // then
        ArgumentCaptor<List<OrderDetail>> captor = ArgumentCaptor.forClass(List.class);
        verify(orderRepository).orderDetailsaveAll(captor.capture());
        List<OrderDetail> capturedOrderDetails = captor.getValue();

        // 검증: 저장된 OrderDetail 리스트 확인
        assertThat(capturedOrderDetails).hasSize(3);
        assertThat(capturedOrderDetails.get(0).getProductId()).isEqualTo(101L);
        assertThat(capturedOrderDetails.get(0).getOrderQuantity()).isEqualTo(2);
        assertThat(capturedOrderDetails.get(0).getOrderAmount()).isEqualTo(2000L);

        // 검증: 반환된 OrderDetailsResponse 리스트 확인
        assertThat(responses).hasSize(3);
        assertThat(responses.get(0).productId()).isEqualTo(101L);
        assertThat(responses.get(0).orderQuantity()).isEqualTo(2);
        assertThat(responses.get(0).orderAmount()).isEqualTo(2000L);
    }

    @Test
    void 주문_상세정보를_저장하면_OrderDetailsResponse_반환() {
        // given
        OrderDetailService orderDetailService = new OrderDetailService(orderRepository);

        // CreateOrderDetailsRequest 객체 생성
        OrderDetailsCreateRequest request = new OrderDetailsCreateRequest(
                1L,
                List.of(101L, 102L, 103L),
                List.of(2, 3, 1),
                List.of(2000L, 3000L, 1000L)
        );

        // Mock 데이터 생성
        List<OrderDetail> mockOrderDetails = List.of(
                OrderDetail.createOrderDetail(request.orderId(), 101L, 2, 2000L),
                OrderDetail.createOrderDetail(request.orderId(), 102L, 3, 3000L),
                OrderDetail.createOrderDetail(request.orderId(), 103L, 1, 1000L)
        );

        // Mock 설정: orderDetailsaveAll 호출 시 Mock 데이터 반환
        given(orderRepository.orderDetailsaveAll(anyList())).willReturn(mockOrderDetails);

        // when
        List<OrderDetailsResponse> responses = orderDetailService.createOrderDetails(request);

        // then
        // 저장된 OrderDetail 리스트 검증
        assertThat(responses).hasSize(3);
        assertThat(responses.get(0).productId()).isEqualTo(101L);
        assertThat(responses.get(0).orderQuantity()).isEqualTo(2);
        assertThat(responses.get(0).orderAmount()).isEqualTo(2000L);

        assertThat(responses.get(1).productId()).isEqualTo(102L);
        assertThat(responses.get(1).orderQuantity()).isEqualTo(3);
        assertThat(responses.get(1).orderAmount()).isEqualTo(3000L);

        assertThat(responses.get(2).productId()).isEqualTo(103L);
        assertThat(responses.get(2).orderQuantity()).isEqualTo(1);
        assertThat(responses.get(2).orderAmount()).isEqualTo(1000L);

        // 호출 검증
        verify(orderRepository).orderDetailsaveAll(anyList());
    }

}