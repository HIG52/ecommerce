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
@Tag(name = "Order API", description = "주문 관리 API")
public class OrderController {

    private final OrderUsecase orderUsecase;

    @Operation(
            summary = "주문 생성",
            description = "쿠폰 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "쿠폰목록 조회 성공",
                            content = @Content(schema = @Schema(implementation = OrderResponseDTO.class)))
            }
    )
    @PostMapping("/api/orders/")
    public ResponseEntity<OrderResponseDTO> createOrders(
            @RequestBody OrderRequestDTO orderRequestDTO) {

        OrderResponseDTO orderResponseDTO = orderUsecase.createOrder(orderRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTO);
    }

    @Operation(
            summary = "주문 조회",
            description = "주문 상세정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주문 상세정보 조회 성공",
                            content = @Content(schema = @Schema(implementation = OrderResponseDTO.class)))
            }
    )
    @GetMapping("/api/orders/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(
            @PathVariable(name = "orderId") int orderId,
            @RequestBody OrderRequestDTO orderRequestDTO) {

        // TODO : 주문 조회 usecase 작성 예정

        return null;
    }

}
