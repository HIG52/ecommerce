package kr.hhplus.be.server.order.domain.repository;

import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.entity.OrderDetail;
import kr.hhplus.be.server.order.domain.entity.ProductIdProjection;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository {
    Order save(Order order);

    List<OrderDetail> orderDetailsaveAll(List<OrderDetail> orderDetails);

    Order findByOrderId(long orderId);

    List<ProductIdProjection> findTop5OrderDetailsGroupByProductId(LocalDateTime startDate, LocalDateTime endDate);

    Order findByOrderIdWithLock(long orderId);
}
