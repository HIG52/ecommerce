package kr.hhplus.be.server.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
public class Order {

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modify_at")
    private LocalDateTime modifyAt;


}
