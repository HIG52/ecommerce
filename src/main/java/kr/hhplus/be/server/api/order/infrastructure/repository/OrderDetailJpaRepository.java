package kr.hhplus.be.server.api.order.infrastructure.repository;

import kr.hhplus.be.server.api.order.domain.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetail, Long> {

}
