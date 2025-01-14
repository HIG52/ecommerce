package kr.hhplus.be.server.order.domain.service.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.order.domain.entity.OrderDetail;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.order.domain.service.OrderDetailService;
import kr.hhplus.be.server.order.domain.service.request.OrderDetailsCreateRequest;
import kr.hhplus.be.server.order.domain.service.info.OrderDetailsInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private OrderDetailService orderDetailService;

    private OrderDetailsCreateRequest orderDetailsCreateRequest;
    private OrderDetail orderDetail;

    @BeforeEach
    void setUp() {
        orderDetailsCreateRequest = new OrderDetailsCreateRequest(
                1L,
                List.of(1L, 2L),
                List.of(5, 10),
                List.of(1000L, 2000L)
        );

        orderDetail = OrderDetail.createOrderDetail(1L, 1L, 5, 1000L);
    }

    @Test
    @DisplayName("주문 상세 요청 정보를 입력하면 주문 상세가 생성된다")
    void createOrderDetails_Success() {
        // given
        given(orderRepository.orderDetailsaveAll(anyList())).willReturn(List.of(orderDetail));

        // when
        List<OrderDetailsInfo> responses = orderDetailService.createOrderDetails(orderDetailsCreateRequest);

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).orderId()).isEqualTo(1L);
        assertThat(responses.get(0).productId()).isEqualTo(1L);
        assertThat(responses.get(0).orderQuantity()).isEqualTo(5);
        assertThat(responses.get(0).orderAmount()).isEqualTo(1000L);

        verify(orderRepository).orderDetailsaveAll(anyList());
    }

    @Test
    @DisplayName("주문 상세 생성 중 예외가 발생하면 CustomExceptionHandler를 반환한다")
    void createOrderDetails_Exception() {
        // given
        given(orderRepository.orderDetailsaveAll(List.of(orderDetail))).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThatThrownBy(() -> orderDetailService.createOrderDetails(orderDetailsCreateRequest))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.ORDER_NOT_CREATE.getMessage());
    }

}