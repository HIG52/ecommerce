package kr.hhplus.be.server.api.payment.infrastructure.repository;

import kr.hhplus.be.server.api.payment.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

    Payment findByPaymentId(long paymentId);
}
