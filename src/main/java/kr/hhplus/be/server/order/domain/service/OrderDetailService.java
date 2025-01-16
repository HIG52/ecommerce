package kr.hhplus.be.server.order.domain.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.order.domain.entity.OrderDetail;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.order.domain.service.request.OrderDetailsCreateRequest;
import kr.hhplus.be.server.order.domain.service.info.OrderDetailsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderRepository orderRepository;

    public List<OrderDetailsInfo> createOrderDetails(OrderDetailsCreateRequest orderDetailsCreateRequest) {
        try {
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (int i = 0; i < orderDetailsCreateRequest.productIds().size(); i++) {
                OrderDetail orderDetail = OrderDetail.createOrderDetail(
                        orderDetailsCreateRequest.orderId(),
                        orderDetailsCreateRequest.productIds().get(i),
                        orderDetailsCreateRequest.productQuantities().get(i),
                        orderDetailsCreateRequest.productPrices().get(i)
                );
                orderDetails.add(orderDetail);
            }

            List<OrderDetail> resultOrderDetails = orderRepository.orderDetailsaveAll(orderDetails);

            // OrderDetailsResponse로 변환
            return resultOrderDetails.stream()
                    .map(orderDetail -> new OrderDetailsInfo(
                            orderDetail.getOrderId(),
                            orderDetail.getProductId(),
                            orderDetail.getOrderQuantity(),
                            orderDetail.getOrderAmount()
                    ))
                    .toList();
        }catch (Exception e){
            throw new CustomExceptionHandler(ErrorCode.ORDER_NOT_CREATE, e);
        }


    }

    public List<OrderDetailsInfo> getTopOrderDetails() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(3);
        List<OrderDetail> orderDetails = orderRepository.findTop5OrderDetailsGroupByProductId(startDate, endDate);

        return orderDetails.stream()
                .map(orderDetail -> new OrderDetailsInfo(
                        orderDetail.getOrderId(),
                        orderDetail.getProductId(),
                        orderDetail.getOrderQuantity(),
                        orderDetail.getOrderAmount()
                ))
                .toList();
    }

}
