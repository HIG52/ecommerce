package kr.hhplus.be.server.api.product.domain.service;

import kr.hhplus.be.server.api.product.domain.entity.Product;
import kr.hhplus.be.server.api.product.domain.repository.ProductRepository;
import kr.hhplus.be.server.api.product.domain.service.dto.QuantityRequest;
import kr.hhplus.be.server.api.product.domain.service.dto.QuantityResponse;
import kr.hhplus.be.server.api.product.presentation.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void 페이지와_사이즈를_입력하면_상품목록_반환() {
        // given
        ProductService productService = new ProductService(productRepository);
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        List<Product> products = List.of(
                Product.createProduct("Product1", 10000L, 10),
                Product.createProduct("Product2", 20000L, 5)
        );

        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());
        given(productRepository.findAll(pageable)).willReturn(productPage);

        // when
        Page<ProductResponseDTO> productResponseDTOPage = productService.getProducts(page, size);

        // then
        assertEquals(productResponseDTOPage.getContent().size(), 2);

        ProductResponseDTO firstProduct = productResponseDTOPage.getContent().get(0);
        assertEquals(firstProduct.getProductName(), "Product1");
        assertEquals(firstProduct.getProductPrice(), 10000L);
        assertEquals(firstProduct.getProductQuantity(), 10);

        ProductResponseDTO secondProduct = productResponseDTOPage.getContent().get(1);
        assertEquals(secondProduct.getProductName(), "Product2");
        assertEquals(secondProduct.getProductPrice(), 20000L);
        assertEquals(secondProduct.getProductQuantity(), 5);
    }
    
    @Test
    void ProductId와_Quantity를_입력받으면_Quantity만큼_재고차감후_반환(){
        //given
        ProductService productService = new ProductService(productRepository);
        QuantityRequest quantityRequest = new QuantityRequest(1L, 2);
        given(productRepository.getProduct(quantityRequest.productId()))
                .willReturn(Product.createProduct("test", 10000L, 10));

        //when
        QuantityResponse quantityResponse = productService.updateProductQuantity(quantityRequest);

        //then
        assertThat(quantityResponse.productId()).isEqualTo(1L);
        assertThat(quantityResponse.productQuantity()).isEqualTo(8);

    }
    
}