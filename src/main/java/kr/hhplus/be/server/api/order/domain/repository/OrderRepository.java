package kr.hhplus.be.server.api.order.domain.repository;

import kr.hhplus.be.server.api.order.domain.entity.Order;
import kr.hhplus.be.server.api.order.domain.entity.OrderDetail;

import java.util.List;

public interface OrderRepository {
    Order save(Order order);

    List<OrderDetail> orderDetailsaveAll(List<OrderDetail> orderDetails);

    Order findByOrderId(long orderId);
}
