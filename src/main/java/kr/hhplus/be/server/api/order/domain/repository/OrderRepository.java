package kr.hhplus.be.server.api.order.domain.repository;

import kr.hhplus.be.server.api.order.domain.entity.Order;
import kr.hhplus.be.server.api.order.domain.entity.OrderDetail;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository {
    Order save(Order order);

    List<OrderDetail> orderDetailsaveAll(List<OrderDetail> orderDetails);

    Order findByOrderId(long orderId);

    List<OrderDetail> findTop3OrderDetailsGroupByProductId(LocalDateTime startDate, LocalDateTime endDate);
}
