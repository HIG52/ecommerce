package kr.hhplus.be.server.order.domain.service;

import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.order.domain.service.response.OrderPaymentStatusResponse;
import kr.hhplus.be.server.order.domain.service.response.OrderResponse;
import kr.hhplus.be.server.order.domain.service.response.OrderStatusResponse;
import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(long userId, long orderTotalAmount) {
        Order order = Order.createOrder(userId, orderTotalAmount, PaymentStatusType.PENDING, OrderStatusType.ORDERED);
        Order resultOrder = orderRepository.save(order);

        return new OrderResponse(
                resultOrder.getOrderId(),
                resultOrder.getUserId(),
                resultOrder.getOrderTotalAmount(),
                resultOrder.getPaymentStatus(),
                resultOrder.getStatus()
        );
    }

    @Transactional
    public OrderStatusResponse updateOrderStatus(long orderId, OrderStatusType orderStatusType) {
        Order order = orderRepository.findByOrderId(orderId);
        order.updateStatus(orderStatusType);
        Order resultOrder = orderRepository.save(order);

        return new OrderStatusResponse(
                resultOrder.getOrderId(),
                resultOrder.getStatus()
        );
    }

    @Transactional
    public OrderPaymentStatusResponse updateOrderPaymentStatus(long orderId, PaymentStatusType paymentStatusType) {
        Order order = orderRepository.findByOrderId(orderId);
        order.updatePaymentStatus(paymentStatusType);
        Order resultOrder = orderRepository.save(order);

        return new OrderPaymentStatusResponse(
                resultOrder.getOrderId(),
                resultOrder.getPaymentStatus()
        );
    }

}
