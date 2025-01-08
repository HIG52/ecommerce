package kr.hhplus.be.server.api.order.domain.service;

import kr.hhplus.be.server.api.order.domain.entity.OrderDetail;
import kr.hhplus.be.server.api.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.api.order.presentation.dto.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderRepository orderRepository;

    public List<OrderResponseDTO> createOrderDetails(Long orderId, List<Long> productIds, List<Integer> productQuantities, List<Long> productPrices) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            OrderDetail orderDetail = OrderDetail.createOrderDetail(
                    orderId,
                    productIds.get(i),
                    productQuantities.get(i),
                    productPrices.get(i)
            );
            orderDetails.add(orderDetail);
        }

        List<OrderDetail> resultOrderDetails = orderRepository.orderDetailsaveAll(orderDetails);

        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
        for (OrderDetail orderDetail : resultOrderDetails) {
            orderResponseDTOs.add(OrderResponseDTO.builder()
                    .orderId(orderDetail.getOrderId())
                    .productId(orderDetail.getProductId())
                    .orderQuantity(orderDetail.getOrderQuantity())
                    .orderAmount(orderDetail.getOrderAmount())
                    .build());
        }

        return orderResponseDTOs;

    }

}
