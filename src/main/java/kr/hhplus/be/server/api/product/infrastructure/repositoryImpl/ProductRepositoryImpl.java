package kr.hhplus.be.server.api.product.infrastructure.repositoryImpl;

import kr.hhplus.be.server.api.product.domain.entity.Product;
import kr.hhplus.be.server.api.product.domain.repository.ProductRepository;
import kr.hhplus.be.server.api.product.infrastructure.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product getProduct(long productId) {
        return productJpaRepository.findByProductId(productId);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productJpaRepository.findAll(pageable);
    }

    @Override
    public void productSave(Product product) {
        productJpaRepository.save(product);
    }

    @Override
    public List<Product> getTopProducts(List<Long> productIds) {
        return productJpaRepository.findByProductIds(productIds);
    }

    @Override
    public Product getProductWithLock(long productId) {
        return productJpaRepository.findByProductIdWithLock(productId);
    }


}
