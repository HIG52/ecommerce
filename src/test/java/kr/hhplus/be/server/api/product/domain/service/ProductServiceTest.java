package kr.hhplus.be.server.api.product.domain.service;

import kr.hhplus.be.server.api.product.domain.entity.Product;
import kr.hhplus.be.server.api.product.domain.repository.ProductRepository;
import kr.hhplus.be.server.api.product.presentation.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Test
    void productId를_입력하면_상품상세_반환() {
        //given
        ProductService productService = new ProductService(productRepository);
        long productId = 1L;
        given(productRepository.getProduct(productId))
                .willReturn(Product.createProduct("test", 10000L, 10));
        //when
        ProductResponseDTO productResponseDTO = productService.getProduct(productId);

        //then
        assertEquals(productResponseDTO.getProductName(), "test");
        assertEquals(productResponseDTO.getProductPrice(), 10000L);
        assertEquals(productResponseDTO.getProductQuantity(), 10);
    }
}