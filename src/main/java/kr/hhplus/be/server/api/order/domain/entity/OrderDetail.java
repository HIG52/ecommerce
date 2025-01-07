package kr.hhplus.be.server.api.order.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.AuditingFields;

@Entity
@Table(name = "order_detail")
public class OrderDetail extends AuditingFields {

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
}
