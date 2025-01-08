package kr.hhplus.be.server.api.payment.domain.service;

import kr.hhplus.be.server.api.payment.domain.entity.Payment;
import kr.hhplus.be.server.api.payment.domain.repository.PaymentRepository;
import kr.hhplus.be.server.api.payment.domain.service.dto.PaymentResponse;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;


    @Test
    void 결제_생성하면_PaymentResponse_반환() {
        // given
        PaymentService paymentService = new PaymentService(paymentRepository);
        long orderId = 1L;
        long paymentAmount = 5000L;
        PaymentStatusType paymentStatus = PaymentStatusType.SUCCESS;

        // Mock Payment 객체 생성
        Payment mockPayment = Payment.createPayment(orderId, paymentAmount, paymentStatus);

        // Mock Payment ID 설정
        ReflectionTestUtils.setField(mockPayment, "paymentId", 100L);

        // Mock 동작 설정
        given(paymentRepository.paymentSave(any(Payment.class))).willReturn(mockPayment);

        // when
        PaymentResponse response = paymentService.createPayment(orderId, paymentAmount, paymentStatus);

        // then
        assertThat(response.paymentId()).isEqualTo(100L);
        assertThat(response.orderId()).isEqualTo(orderId);
        assertThat(response.paymentAmount()).isEqualTo(paymentAmount);
        assertThat(response.paymentStatus()).isEqualTo(paymentStatus);
    }

}