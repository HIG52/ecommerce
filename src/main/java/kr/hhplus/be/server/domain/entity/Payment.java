package kr.hhplus.be.server.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "payment_amount")
    private Long paymentAmount;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modify_at")
    private LocalDateTime modifyAt;

}
