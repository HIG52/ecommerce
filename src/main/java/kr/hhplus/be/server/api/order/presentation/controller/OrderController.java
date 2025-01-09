package kr.hhplus.be.server.api.order.presentation.controller;

import kr.hhplus.be.server.api.order.presentation.dto.OrderRequestDTO;
import kr.hhplus.be.server.api.order.presentation.dto.OrderResponseDTO;
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

        // TODO : 주문 생성 usecase 작성 예정

        return null;
    }

    @GetMapping("/api/orders/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(
            @PathVariable(name = "orderId") int orderId,
            @RequestBody OrderRequestDTO orderRequestDTO) {

        // TODO : 주문 조회 usecase 작성 예정

        return null;
    }

}
