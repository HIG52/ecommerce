package kr.hhplus.be.server.product.domain.repository;

import kr.hhplus.be.server.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {

    Product getProduct(long productId);


    Page<Product> findAll(Pageable pageable);

    void productSave(Product product);

    List<Product> getTopProducts(List<Long> productIds);

    Product getProductWithLock(long l);
}
