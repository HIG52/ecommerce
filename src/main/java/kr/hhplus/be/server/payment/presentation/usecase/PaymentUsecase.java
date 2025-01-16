package kr.hhplus.be.server.payment.presentation.usecase;

import kr.hhplus.be.server.balance.domain.service.BalanceService;
import kr.hhplus.be.server.balance.domain.service.request.BalanceDecreaseRequest;
import kr.hhplus.be.server.balance.domain.service.info.BalanceInfo;
import kr.hhplus.be.server.order.domain.service.OrderService;
import kr.hhplus.be.server.payment.domain.service.PaymentService;
import kr.hhplus.be.server.payment.domain.service.request.PaymentCreateRequest;
import kr.hhplus.be.server.payment.domain.service.info.PaymentInfo;
import kr.hhplus.be.server.payment.presentation.dto.PaymentRequestDTO;
import kr.hhplus.be.server.payment.presentation.dto.PaymentResponseDTO;
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

        //주문 상태확인 락생성
        orderService.checkOrderPendingStatus(paymentRequestDTO.orderId());

        //잔액차감
        BalanceDecreaseRequest balanceDecreaseRequest =
                new BalanceDecreaseRequest(paymentRequestDTO.userId(), paymentRequestDTO.paymentAmount());
        BalanceInfo balanceInfo = balanceService.decreaseBalance(balanceDecreaseRequest);

        //결제
        PaymentCreateRequest paymentCreateRequest =
                new PaymentCreateRequest(paymentRequestDTO.orderId(), paymentRequestDTO.paymentAmount(), paymentRequestDTO.couponId(), PaymentStatusType.SUCCESS);
        PaymentInfo paymentInfo = paymentService.createPayment(paymentCreateRequest);
        
        //주문 상태 변경
        orderService.updateOrderPaymentStatus(paymentRequestDTO.orderId(), PaymentStatusType.SUCCESS);
        orderService.updateOrderStatus(paymentRequestDTO.orderId(), OrderStatusType.PAYMENT_COMPLETED);

        //데이터플랫폼 데이터 전송
        boolean isData = dataPlatformService.sendData(balanceInfo);
        if(!isData) {
            throw new IllegalStateException("데이터 전송 실패");
        }
        return new PaymentResponseDTO(
                paymentInfo.paymentId()
        );
    }



}
