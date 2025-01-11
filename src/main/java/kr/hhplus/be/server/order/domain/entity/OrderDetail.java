package kr.hhplus.be.server.order.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.entity.AuditingFields;
import lombok.Getter;


@Entity
@Getter
@Table(name = "order_detail")
public class OrderDetail extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id", nullable = false)
    private Long orderDetailId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "order_quantity")
    private Integer orderQuantity;

    @Column(name = "order_amount")
    private Long orderAmount;

    protected OrderDetail() {
    }

    private OrderDetail(Long orderId, Long productId, Integer orderQuantity, Long orderAmount) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
    }

    public static OrderDetail createOrderDetail(Long orderId, Long productId, Integer orderQuantity, Long orderAmount) {
        return new OrderDetail(orderId, productId, orderQuantity, orderAmount);
    }
}
