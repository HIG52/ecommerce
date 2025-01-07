package kr.hhplus.be.server.api.payment.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.AuditingFields;

@Entity
@Table(name = "payment")
public class Payment extends AuditingFields {

    @Id
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "payment_amount")
    private Long paymentAmount;

    @Column(name = "payment_status")
    private String paymentStatus;

}
