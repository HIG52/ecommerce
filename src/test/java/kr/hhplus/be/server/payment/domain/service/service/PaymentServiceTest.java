package kr.hhplus.be.server.payment.domain.service.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.payment.domain.entity.Payment;
import kr.hhplus.be.server.payment.domain.repository.PaymentRepository;
import kr.hhplus.be.server.payment.domain.service.PaymentService;
import kr.hhplus.be.server.payment.domain.service.request.PaymentCreateRequest;
import kr.hhplus.be.server.payment.domain.service.info.PaymentInfo;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.createPayment(1L, 2L, 5000L, PaymentStatusType.SUCCESS);
        ReflectionTestUtils.setField(payment, "paymentId", 1L);
    }

    @Test
    @DisplayName("결제 요청 정보를 입력하면 결제가 생성된다")
    void createPayment_Success() {
        // given
        PaymentCreateRequest request = new PaymentCreateRequest(1L, 5000L, 2L, PaymentStatusType.SUCCESS);
        given(paymentRepository.paymentSave(org.mockito.ArgumentMatchers.any(Payment.class))).willReturn(payment);

        // when
        PaymentInfo response = paymentService.createPayment(request);

        // then
        assertThat(response.paymentId()).isEqualTo(1L);
        assertThat(response.orderId()).isEqualTo(1L);
        assertThat(response.couponId()).isEqualTo(2L);
        assertThat(response.paymentAmount()).isEqualTo(5000L);
        assertThat(response.paymentStatus()).isEqualTo(PaymentStatusType.SUCCESS);

        verify(paymentRepository).paymentSave(org.mockito.ArgumentMatchers.any(Payment.class));
    }

    @Test
    @DisplayName("결제 생성 중 저장 실패 시 CustomExceptionHandler를 반환한다")
    void createPayment_SaveFailed() {
        // given
        PaymentCreateRequest request = new PaymentCreateRequest(1L, 5000L, 2L, PaymentStatusType.SUCCESS);
        given(paymentRepository.paymentSave(any(Payment.class))).willReturn(null);

        // when & then
        assertThatThrownBy(() -> paymentService.createPayment(request))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.PAYMENT_SAVE_FAILED.getMessage());
    }

    @Test
    @DisplayName("결제 ID와 상태를 입력하면 결제 상태가 업데이트된다")
    void updatePaymentType_Success() {
        // given
        given(paymentRepository.paymentFindById(1L)).willReturn(payment);
        given(paymentRepository.paymentSave(payment)).willReturn(payment);

        // when
        PaymentInfo response = paymentService.updatePaymentType(1L, PaymentStatusType.SUCCESS);

        // then
        assertThat(response.paymentId()).isEqualTo(1L);
        assertThat(response.paymentStatus()).isEqualTo(PaymentStatusType.SUCCESS);

        verify(paymentRepository).paymentFindById(1L);
        verify(paymentRepository).paymentSave(payment);
    }

    @Test
    @DisplayName("결제 상태 업데이트 중 저장 실패 시 CustomExceptionHandler를 반환한다")
    void updatePaymentType_SaveFailed() {
        // given
        given(paymentRepository.paymentFindById(1L)).willReturn(payment);
        given(paymentRepository.paymentSave(payment)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> paymentService.updatePaymentType(1L, PaymentStatusType.SUCCESS))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.PAYMENT_STATUS_UPDATE_FAIL.getMessage());
    }

}