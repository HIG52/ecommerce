package kr.hhplus.be.server.api.controller;

import kr.hhplus.be.server.api.dto.OrderRequestDTO;
import kr.hhplus.be.server.api.dto.OrderResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @PostMapping("/api/orders/")
    public ResponseEntity<OrderResponseDTO> orders(
            @RequestBody OrderRequestDTO orderRequestDTO) {

        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(1);

        if(orderRequestDTO.getUserId() <= 0) {
            response.setMessage("존재하지 않는 유저입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        for (int i = 0; i < orderRequestDTO.getProductIds().size(); i++) {
            if(orderRequestDTO.getProductQuantities().get(i) <= 0) {
                response.setMessage("품절된 상품이 존재합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/orders/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(
            @PathVariable(name = "orderId") int orderId,
            @RequestBody OrderRequestDTO orderRequestDTO) {

        OrderResponseDTO response = new OrderResponseDTO();

        // 기본 정보 설정
        response.setOrderId(orderId);
        response.setUserId(orderRequestDTO.getUserId());
        response.setTotalPrice(10000);
        response.setStatus("ORDERED");

        // OrderDetails 리스트 생성
        List<OrderResponseDTO.OrderDetail> orderDetails = new ArrayList<>();

        // 샘플 데이터 추가
        OrderResponseDTO.OrderDetail detail1 = new OrderResponseDTO.OrderDetail();
        detail1.setProductId(2L);
        detail1.setProductQuantity(3);
        detail1.setProductPrice(2000);

        OrderResponseDTO.OrderDetail detail2 = new OrderResponseDTO.OrderDetail();
        detail2.setProductId(3L);
        detail2.setProductQuantity(2);
        detail2.setProductPrice(4000);

        // 리스트에 추가
        orderDetails.add(detail1);
        orderDetails.add(detail2);

        // OrderDetails 리스트를 Response에 설정
        response.setOrderDetails(orderDetails);

        // 유효성 검사
        if (orderRequestDTO.getUserId() <= 0) {
            response.setMessage("사용자가 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
