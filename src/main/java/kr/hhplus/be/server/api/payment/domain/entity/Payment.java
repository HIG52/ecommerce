package kr.hhplus.be.server.api.payment.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.entity.AuditingFields;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import lombok.Getter;

@Entity
@Getter
@Table(name = "payment")
public class Payment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "payment_amount")
    private Long paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatusType paymentStatus;

    protected Payment() {
    }

    private Payment(Long orderId, Long paymentAmount, PaymentStatusType paymentStatus) {
        this.orderId = orderId;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
    }

    public static Payment createPayment(Long orderId, Long paymentAmount, PaymentStatusType paymentStatus) {
        return new Payment(orderId, paymentAmount, paymentStatus);
    }

    public void updatePaymentStatus(PaymentStatusType paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
