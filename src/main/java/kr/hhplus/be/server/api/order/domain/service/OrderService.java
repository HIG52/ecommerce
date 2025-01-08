package kr.hhplus.be.server.api.order.domain.service;

import kr.hhplus.be.server.api.order.domain.entity.Order;
import kr.hhplus.be.server.api.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.api.order.presentation.dto.OrderResponseDTO;
import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponseDTO createOrder(long userId, long orderTotalAmount) {
            Order order = Order.createOrder(userId, orderTotalAmount, PaymentStatusType.PENDING, OrderStatusType.ORDERED);
            Order resultOrder = orderRepository.save(order);
        // TODO : 서비스에서 컨트롤러 또는 usecase로 내려줄 데이터 전달객체 생성예정
        return OrderResponseDTO.builder()
                .userId(resultOrder.getUserId())
                .totalPrice(resultOrder.getOrderTotalAmount())
                .paymentStatus(resultOrder.getPaymentStatus())
                .status(resultOrder.getStatus())
                .build();
    }

    public OrderResponseDTO updateOrderStatus(long orderId, OrderStatusType orderStatusType) {
        Order order = orderRepository.findByOrderId(orderId);
        order.updateStatus(orderStatusType);
        Order resultOrder = orderRepository.save(order);
        // TODO : status만 반환하도록 수정 예정
        return OrderResponseDTO.builder()
                .userId(resultOrder.getUserId())
                .totalPrice(resultOrder.getOrderTotalAmount())
                .paymentStatus(resultOrder.getPaymentStatus())
                .status(resultOrder.getStatus())
                .build();
    }


}
