package kr.hhplus.be.server.api.product.domain.repository;

import kr.hhplus.be.server.api.product.domain.entity.Product;

public interface ProductRepository {

    Product getProduct(long productId);

}
