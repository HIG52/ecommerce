package kr.hhplus.be.server.payment.domain.service;

import kr.hhplus.be.server.payment.domain.entity.Payment;
import kr.hhplus.be.server.payment.domain.repository.PaymentRepository;
import kr.hhplus.be.server.payment.domain.service.request.PaymentCreateRequest;
import kr.hhplus.be.server.payment.domain.service.response.PaymentResponse;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponse createPayment(PaymentCreateRequest paymentCreateRequest) {

        Payment payment = Payment.createPayment(paymentCreateRequest.orderId(), paymentCreateRequest.paymentAmount(), paymentCreateRequest.couponId(), PaymentStatusType.PENDING);
        Payment resultPayment = paymentRepository.paymentSave(payment);

        return new PaymentResponse(
                resultPayment.getPaymentId(),
                resultPayment.getOrderId(),
                resultPayment.getCouponId(),
                resultPayment.getPaymentAmount(),
                resultPayment.getPaymentStatus());
    }

    @Transactional
    public PaymentResponse updatePaymentType(long paymentId, PaymentStatusType paymentStatus) {

        Payment payment = paymentRepository.paymentFindById(paymentId);
        payment.updatePaymentStatus(paymentStatus);
        Payment resultPayment = paymentRepository.paymentSave(payment);

        return new PaymentResponse(
                resultPayment.getPaymentId(),
                resultPayment.getOrderId(),
                resultPayment.getCouponId(),
                resultPayment.getPaymentAmount(),
                resultPayment.getPaymentStatus());
    }

}
