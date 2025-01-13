package kr.hhplus.be.server.payment.domain.entity;

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

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "payment_amount")
    private Long paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatusType paymentStatus;

    protected Payment() {
    }

    private Payment(Long orderId, Long couponId, Long paymentAmount, PaymentStatusType paymentStatus) {
        this.orderId = orderId;
        this.couponId = couponId;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
    }

    public static Payment createPayment(Long orderId, Long couponId, Long paymentAmount, PaymentStatusType paymentStatus) {
        return new Payment(orderId, couponId, paymentAmount, paymentStatus);
    }

    public void updatePaymentStatus(PaymentStatusType paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
