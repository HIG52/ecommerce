package kr.hhplus.be.server.api.payment.domain.repository;

import kr.hhplus.be.server.api.payment.domain.entity.Payment;

public interface PaymentRepository {

    Payment paymentSave(Payment payment);

    Payment paymentFindById(long paymentId);
}
