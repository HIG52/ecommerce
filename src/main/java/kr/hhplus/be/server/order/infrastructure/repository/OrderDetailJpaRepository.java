package kr.hhplus.be.server.order.infrastructure.repository;

import kr.hhplus.be.server.order.domain.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetail, Long> {

    @Query(value = "SELECT MAX(order_detail_id) AS order_detail_id, MAX(order_id) AS order_id, MAX(product_id) AS product_id, SUM(order_quantity) AS order_quantity, MAX(order_amount) AS order_amount, MAX(created_at) AS created_at, MAX(modify_at) AS modify_at " +
            "FROM order_detail " +
            "WHERE created_at BETWEEN NOW() - INTERVAL 3 DAY AND NOW() " +
            "GROUP BY product_id " +
            "ORDER BY SUM(order_quantity) DESC " +
            "LIMIT 5", nativeQuery = true)
    List<OrderDetail> findTop5OrderDetailsGroupByProductId(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
