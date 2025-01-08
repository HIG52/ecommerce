package kr.hhplus.be.server.api.payment.infrastructure.repositoryImpl;

import kr.hhplus.be.server.api.payment.domain.entity.Payment;
import kr.hhplus.be.server.api.payment.domain.repository.PaymentRepository;
import kr.hhplus.be.server.api.payment.infrastructure.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment paymentSave(Payment payment) {
        return paymentJpaRepository.save(payment);
    }

    @Override
    public Payment paymentFindById(long paymentId) {
        return paymentJpaRepository.findByPaymentId(paymentId);
    }
}
