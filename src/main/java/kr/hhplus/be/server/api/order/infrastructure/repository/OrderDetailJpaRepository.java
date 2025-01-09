package kr.hhplus.be.server.api.order.infrastructure.repository;

import kr.hhplus.be.server.api.order.domain.entity.OrderDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetail, Long> {

    @Query(value = "SELECT product_id, SUM(order_quantity) AS total_quantity " +
            "FROM order_detail " +
            "WHERE created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY product_id " +
            "ORDER BY total_quantity DESC " +
            "LIMIT 3", nativeQuery = true)
    List<OrderDetail> findTop3OrderDetailsGroupByProductId(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
