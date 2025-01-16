package kr.hhplus.be.server.order.domain.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.order.domain.service.info.OrderPaymentStatusInfo;
import kr.hhplus.be.server.order.domain.service.info.OrderInfo;
import kr.hhplus.be.server.order.domain.service.info.OrderStatusInfo;
import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderInfo createOrder(long userId, long orderTotalAmount) {
        Order order = Order.createOrder(userId, orderTotalAmount, PaymentStatusType.PENDING, OrderStatusType.ORDERED);
        Order resultOrder = orderRepository.save(order);
        if(resultOrder == null){
            throw new CustomExceptionHandler(ErrorCode.ORDER_NOT_CREATE);
        }
        return new OrderInfo(
                resultOrder.getOrderId(),
                resultOrder.getUserId(),
                resultOrder.getOrderTotalAmount(),
                resultOrder.getPaymentStatus(),
                resultOrder.getStatus()
        );
    }

    @Transactional
    public OrderStatusInfo updateOrderStatus(long orderId, OrderStatusType orderStatusType) {
        Order order = orderRepository.findByOrderId(orderId);
        order.updateStatus(orderStatusType);
        Order resultOrder = orderRepository.save(order);
        if(resultOrder == null){
            throw new CustomExceptionHandler(ErrorCode.ORDER_STATUS_UPDATE_FAIL);
        }
        return new OrderStatusInfo(
                resultOrder.getOrderId(),
                resultOrder.getStatus()
        );
    }

    @Transactional
    public OrderPaymentStatusInfo updateOrderPaymentStatus(long orderId, PaymentStatusType paymentStatusType) {
        Order order = orderRepository.findByOrderId(orderId);
        order.updatePaymentStatus(paymentStatusType);
        Order resultOrder = orderRepository.save(order);
        if(resultOrder == null){
            throw new CustomExceptionHandler(ErrorCode.ORDER_PAYMENT_STATUS_UPDATE_FAIL);
        }
        return new OrderPaymentStatusInfo(
                resultOrder.getOrderId(),
                resultOrder.getPaymentStatus()
        );
    }

    @Transactional
    public void checkOrderPendingStatus(long orderId) {
        Order order = orderRepository.findByOrderIdWithLock(orderId);
        if(order.getPaymentStatus() != PaymentStatusType.PENDING){
            throw new CustomExceptionHandler(ErrorCode.ORDER_ALREADY_PROCESSED);
        }
    }

}
