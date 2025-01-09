package kr.hhplus.be.server.api.product.infrastructure.repository;

import kr.hhplus.be.server.api.product.domain.entity.Product;
import kr.hhplus.be.server.api.product.domain.service.response.ProductsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Product findByProductId(Long productId);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.productId IN :productIds")
    List<Product> findByProductIds(@Param("productIds") List<Long> productIds);
}
