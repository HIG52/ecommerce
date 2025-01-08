package kr.hhplus.be.server.api.product.domain.repository;

import kr.hhplus.be.server.api.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

    Product getProduct(long productId);


    Page<Product> findAll(Pageable pageable);

    void productSave(Product product);
}
