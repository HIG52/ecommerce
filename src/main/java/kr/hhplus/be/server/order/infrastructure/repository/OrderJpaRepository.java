package kr.hhplus.be.server.order.infrastructure.repository;

import kr.hhplus.be.server.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    Order findByOrderId(long orderId);
}
