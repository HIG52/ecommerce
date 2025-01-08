package kr.hhplus.be.server.api.order.infrastructure.repositoryImpl;

import kr.hhplus.be.server.api.order.domain.entity.Order;
import kr.hhplus.be.server.api.order.domain.entity.OrderDetail;
import kr.hhplus.be.server.api.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.api.order.infrastructure.repository.OrderDetailJpaRepository;
import kr.hhplus.be.server.api.order.infrastructure.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    private final OrderDetailJpaRepository orderDetailJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public List<OrderDetail> orderDetailsaveAll(List<OrderDetail> orderDetails) {
         return orderDetailJpaRepository.saveAll(orderDetails);
    }

    @Override
    public Order findByOrderId(long orderId) {
        return orderJpaRepository.findByOrderId(orderId);
    }
}
