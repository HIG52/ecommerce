package kr.hhplus.be.server.payment.domain.entity;

import kr.hhplus.be.server.common.type.PaymentStatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.createPayment(1L, 2L, 5000L, PaymentStatusType.SUCCESS);
        ReflectionTestUtils.setField(payment, "paymentId", 1L);
    }

    @Test
    @DisplayName("Payment 객체 생성 후 필드 값이 올바르게 설정된다")
    void createPayment_Success() {
        // then
        assertThat(payment.getPaymentId()).isEqualTo(1L);
        assertThat(payment.getOrderId()).isEqualTo(1L);
        assertThat(payment.getCouponId()).isEqualTo(2L);
        assertThat(payment.getPaymentAmount()).isEqualTo(5000L);
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatusType.SUCCESS);
    }

    @Test
    @DisplayName("Payment 객체의 상태를 업데이트할 수 있다")
    void updatePaymentStatus_Success() {
        // when
        payment.updatePaymentStatus(PaymentStatusType.SUCCESS);

        // then
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatusType.SUCCESS);
    }

}