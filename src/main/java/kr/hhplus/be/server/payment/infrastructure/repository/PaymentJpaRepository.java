package kr.hhplus.be.server.payment.infrastructure.repository;

import kr.hhplus.be.server.payment.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

    Payment findByPaymentId(long paymentId);
}
