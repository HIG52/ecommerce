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
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_amount")
    private PaymentStatusType paymentAmount;

    @Column(name = "payment_status")
    private String paymentStatus;

    protected Payment() {
    }

    private Payment(Long orderId, PaymentStatusType paymentAmount, String paymentStatus) {
        this.orderId = orderId;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
    }

    public static Payment createPayment(Long orderId, PaymentStatusType paymentAmount, String paymentStatus) {
        return new Payment(orderId, paymentAmount, paymentStatus);
    }

}
