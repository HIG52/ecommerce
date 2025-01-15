package kr.hhplus.be.server.payment.domain.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.payment.domain.entity.Payment;
import kr.hhplus.be.server.payment.domain.repository.PaymentRepository;
import kr.hhplus.be.server.payment.domain.service.request.PaymentCreateRequest;
import kr.hhplus.be.server.payment.domain.service.info.PaymentInfo;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentInfo createPayment(PaymentCreateRequest paymentCreateRequest) {

        Payment payment = Payment.createPayment(paymentCreateRequest.orderId(), paymentCreateRequest.couponId(), paymentCreateRequest.paymentAmount(), PaymentStatusType.PENDING);
        Payment resultPayment = paymentRepository.paymentSave(payment);

        if(resultPayment == null){
            throw new CustomExceptionHandler(ErrorCode.PAYMENT_SAVE_FAILED);
        }

        return new PaymentInfo(
                resultPayment.getPaymentId(),
                resultPayment.getOrderId(),
                resultPayment.getCouponId(),
                resultPayment.getPaymentAmount(),
                resultPayment.getPaymentStatus());
    }

    @Transactional
    public PaymentInfo updatePaymentType(long paymentId, PaymentStatusType paymentStatus) {

        Payment payment = paymentRepository.paymentFindById(paymentId);
        payment.updatePaymentStatus(paymentStatus);
        Payment resultPayment = paymentRepository.paymentSave(payment);

        if(resultPayment == null){
            throw new CustomExceptionHandler(ErrorCode.PAYMENT_STATUS_UPDATE_FAIL);
        }

        return new PaymentInfo(
                resultPayment.getPaymentId(),
                resultPayment.getOrderId(),
                resultPayment.getCouponId(),
                resultPayment.getPaymentAmount(),
                resultPayment.getPaymentStatus());
    }

}
