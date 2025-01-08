package kr.hhplus.be.server.api.order.infrastructure.repository;

import kr.hhplus.be.server.api.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    Order findByOrderId(long orderId);
}
