package kr.hhplus.be.server.order.infrastructure.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    Order findByOrderId(long orderId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Order o WHERE o.orderId = :orderId")
    Order findByOrderIdWithLock(@Param("orderId") long orderId);
}
