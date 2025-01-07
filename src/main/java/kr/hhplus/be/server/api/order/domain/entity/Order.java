package kr.hhplus.be.server.api.order.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import kr.hhplus.be.server.common.entity.AuditingFields;

@Entity
public class Order extends AuditingFields {

    @Id
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_total_amount")
    private Long orderTotalAmount;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "status")
    private String status;

}