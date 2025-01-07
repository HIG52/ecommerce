package kr.hhplus.be.server.api.product.infrastructure.repository;

import kr.hhplus.be.server.api.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Product findByProductId(Long productId);

    Page<Product> findAll(Pageable pageable);
}
