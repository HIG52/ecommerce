package kr.hhplus.be.server.api.product.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.AuditingFields;

@Entity
@Table(name = "product")
public class Product extends AuditingFields {

    @Id
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Long productPrice;

    @Column(name = "product_quantity")
    private Integer productQuantity;

}
