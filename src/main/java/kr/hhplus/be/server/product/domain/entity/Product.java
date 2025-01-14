package kr.hhplus.be.server.product.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.entity.AuditingFields;
import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import lombok.Getter;

@Entity
@Getter
@Table(name = "product")
public class Product extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Long productPrice;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    protected Product() {

    }

    private Product(String productName, Long productPrice, Integer productQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public static Product createProduct(String productName, Long productPrice, Integer productQuantity) {
        return new Product(productName, productPrice, productQuantity);
    }

    public void decreaseProductQuantity(int productQuantity) {
        if(this.productQuantity < productQuantity) {
            throw new CustomExceptionHandler(ErrorCode.STOCK_QUANTITY_NOT_ENOUGH);
        }
        this.productQuantity -= productQuantity;
    }
}
