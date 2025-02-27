package kr.hhplus.be.server.order.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.order.presentation.dto.OrderRequestDTO;
import kr.hhplus.be.server.order.presentation.dto.OrderResponseDTO;
import kr.hhplus.be.server.order.presentation.usecase.OrderUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderControllerDocs {

    private final OrderUsecase orderUsecase;

    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponseDTO> createOrders(
            @RequestBody OrderRequestDTO orderRequestDTO) {

        OrderResponseDTO orderResponseDTO = orderUsecase.createOrder(orderRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTO);
    }

    @GetMapping("/api/orders/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(
            @PathVariable(name = "orderId") int orderId,
            @RequestBody OrderRequestDTO orderRequestDTO) {

        // TODO : 주문 조회 usecase 작성 예정, 기본 요구사항에 아직 존재하지 않아 넣지 않았습니다.

        return null;
    }

}
