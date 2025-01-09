package kr.hhplus.be.server.api.payment.presentation.usecase;

import kr.hhplus.be.server.api.balance.domain.service.BalanceService;
import kr.hhplus.be.server.api.balance.domain.service.request.BalanceDecreaseRequest;
import kr.hhplus.be.server.api.balance.domain.service.response.BalanceResponse;
import kr.hhplus.be.server.api.order.domain.service.OrderService;
import kr.hhplus.be.server.api.payment.domain.service.PaymentService;
import kr.hhplus.be.server.api.payment.domain.service.request.PaymentCreateRequest;
import kr.hhplus.be.server.api.payment.domain.service.response.PaymentResponse;
import kr.hhplus.be.server.api.payment.presentation.dto.PaymentRequestDTO;
import kr.hhplus.be.server.api.payment.presentation.dto.PaymentResponseDTO;
import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import kr.hhplus.be.server.dataflatform.DataPlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentUsecase {

    private final PaymentService paymentService;
    private final BalanceService balanceService;
    private final OrderService orderService;
    private final DataPlatformService dataPlatformService;

    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO) {

        //잔액차감 및 락 생성
        BalanceDecreaseRequest balanceDecreaseRequest =
                new BalanceDecreaseRequest(paymentRequestDTO.userId(), paymentRequestDTO.paymentAmount());
        BalanceResponse balanceResponse = balanceService.decreaseBalance(balanceDecreaseRequest);

        //결제
        PaymentCreateRequest paymentCreateRequest =
                new PaymentCreateRequest(paymentRequestDTO.orderId(), paymentRequestDTO.paymentAmount(), paymentRequestDTO.couponId(), PaymentStatusType.SUCCESS);
        PaymentResponse paymentResponse = paymentService.createPayment(paymentCreateRequest);
        
        //주문 상태 변경
        orderService.updateOrderPaymentStatus(paymentRequestDTO.orderId(), PaymentStatusType.SUCCESS);
        orderService.updateOrderStatus(paymentRequestDTO.orderId(), OrderStatusType.PAYMENT_COMPLETED);

        //데이터플랫폼 데이터 전송
        boolean isData = dataPlatformService.sendData(balanceResponse);
        if(!isData) {
            throw new IllegalStateException("데이터 전송 실패");
        }
        return new PaymentResponseDTO(
                paymentResponse.paymentId(),
                isData
        );
    }



}
