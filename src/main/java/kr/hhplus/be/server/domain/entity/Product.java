package kr.hhplus.be.server.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Long productPrice;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modify_at")
    private LocalDateTime modifyAt;

}
