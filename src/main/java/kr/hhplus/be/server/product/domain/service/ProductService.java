package kr.hhplus.be.server.product.domain.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.product.domain.entity.Product;
import kr.hhplus.be.server.product.domain.repository.ProductRepository;
import kr.hhplus.be.server.product.domain.service.request.QuantityRequest;
import kr.hhplus.be.server.product.domain.service.response.ProductResponse;
import kr.hhplus.be.server.product.domain.service.response.ProductsResponse;
import kr.hhplus.be.server.product.domain.service.response.QuantityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse getProduct(long productId) {

        Product product = productRepository.getProduct(productId);

        if(product == null) {
            throw new CustomExceptionHandler(ErrorCode.PRODUCT_NOT_FOUND);
        }

        if(product.getProductQuantity() <= 0) {
            throw new CustomExceptionHandler(ErrorCode.STOCK_QUANTITY_NOT_ENOUGH);
        }

        return new ProductResponse(
                product.getProductName(),
                product.getProductPrice(),
                product.getProductQuantity()
        );
    }

    public List<ProductsResponse> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.stream()
                .map(product -> {
                    return new ProductsResponse(
                            product.getProductId(),
                            product.getProductName(),
                            product.getProductPrice(),
                            product.getProductQuantity()
                    );
                })
                .toList();
    }

    @Transactional
    public QuantityResponse decreaseProductQuantity(QuantityRequest quantityRequests) {
        try {
            Product product = productRepository.getProductWithLock(quantityRequests.productId());
            product.decreaseProductQuantity(quantityRequests.productQuantity());
            productRepository.productSave(product);

            return new QuantityResponse(quantityRequests.productId(), product.getProductQuantity());
        } catch (CustomExceptionHandler e) {
            throw e;
        } catch (Exception e) {
            throw new CustomExceptionHandler(ErrorCode.PRODUCT_NOT_FOUND, e);
        }

    }

    // TODO : 주문에서 리스트를 가져와 상품을 조회하는 usecase 구현 예정
    public List<ProductsResponse> getTopProducts(List<Long> productIds) {

        List<Product> productsResponse = productRepository.getTopProducts(productIds);

        return productsResponse.stream()
                .map(product -> new ProductsResponse(
                        product.getProductId(),
                        product.getProductName(),
                        product.getProductPrice(),
                        product.getProductQuantity()
                ))
                .toList();
    }

}
