package kr.hhplus.be.server.payment.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.payment.presentation.dto.PaymentRequestDTO;
import kr.hhplus.be.server.payment.presentation.dto.PaymentResponseDTO;
import kr.hhplus.be.server.payment.presentation.usecase.PaymentUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "결제 관리 API")
public class PaymentController {

    private final PaymentUsecase paymentUsecase;

    @Operation(
            summary = "결제 생성",
            description = "결제 요청 및 결제 결과 저장",
            responses = {
                    @ApiResponse(responseCode = "200", description = "결제 저장 성공",
                            content = @Content(schema = @Schema(implementation = PaymentResponseDTO.class)))
            }
    )
    @PostMapping("/api/payments/")
    public ResponseEntity<PaymentResponseDTO> payments(
            @RequestBody PaymentRequestDTO paymentRequestDTO) {

        PaymentResponseDTO paymentResponseDTO = paymentUsecase.createPayment(paymentRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
