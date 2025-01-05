package kr.hhplus.be.server.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @Column(name = "order_detail_id", nullable = false)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "order_quantity")
    private Integer orderQuantity;

    @Column(name = "order_amount")
    private Long orderAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modify_at")
    private LocalDateTime modifyAt;
}
