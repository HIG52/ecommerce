package kr.hhplus.be.server.api.order.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.entity.AuditingFields;
import kr.hhplus.be.server.common.type.OrderStatusType;
import kr.hhplus.be.server.common.type.PaymentStatusType;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "order")
public class Order extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_total_amount")
    private Long orderTotalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatusType paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatusType status;

    protected Order() {
    }

    private Order(Long userId, Long orderTotalAmount, PaymentStatusType paymentStatus, OrderStatusType status) {
        this.userId = userId;
        this.orderTotalAmount = orderTotalAmount;
        this.paymentStatus = paymentStatus;
        this.status = status;
    }

    public static Order createOrder(Long userId, Long orderTotalAmount, PaymentStatusType paymentStatus, OrderStatusType status) {
        return new Order(userId, orderTotalAmount, paymentStatus, status);
    }

    public void updateStatus(OrderStatusType status) {
        this.status = status;
    }

}
