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
/*

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentId(1);
        response.setPaymentResult(true);

        if(paymentRequestDTO.getUserId() <= 0) {
            response.setMessage("존재하지 않는 유저입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if(paymentRequestDTO.getOrderId() <= 0) {
            response.setMessage("존재하지 않는 유저입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if(paymentRequestDTO.getUserId() == 10 && paymentRequestDTO.getOrderId() == 10) { //에러를 받기위해 임시로 작성한
            response.setMessage("만료된 주문입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

*/

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
