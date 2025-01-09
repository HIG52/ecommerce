package kr.hhplus.be.server.api.payment.presentation.controller;

import kr.hhplus.be.server.api.payment.presentation.dto.PaymentRequestDTO;
import kr.hhplus.be.server.api.payment.presentation.dto.PaymentResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @PostMapping("/api/payments/")
    public ResponseEntity<PaymentResponseDTO> payments(
            @RequestBody PaymentRequestDTO paymentRequestDTO) {
        
        //TODO usecase 작성 예정

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
