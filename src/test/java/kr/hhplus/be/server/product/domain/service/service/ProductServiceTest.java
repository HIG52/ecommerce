package kr.hhplus.be.server.product.domain.service.service;

import kr.hhplus.be.server.product.domain.entity.Product;
import kr.hhplus.be.server.product.domain.repository.ProductRepository;
import kr.hhplus.be.server.product.domain.service.ProductService;
import kr.hhplus.be.server.product.domain.service.request.QuantityRequest;
import kr.hhplus.be.server.product.domain.service.response.ProductResponse;
import kr.hhplus.be.server.product.domain.service.response.ProductsResponse;
import kr.hhplus.be.server.product.domain.service.response.QuantityResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        ProductResponse productResponse = productService.getProduct(productId);

        //then
        assertEquals(productResponse.productName(), "test");
        assertEquals(productResponse.productPrice(), 10000L);
        assertEquals(productResponse.productQuantity(), 10);
    }

    @Test
    void 존재하지_않는_productId로_조회시_IllegalArgumentException발생() {
        // given
        ProductService productService = new ProductService(productRepository);
        long productId = 1L;

        // Mock 설정: getProduct 호출 시 null 반환
        given(productRepository.getProduct(productId)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> productService.getProduct(productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품이 존재하지 않습니다.");

        // verify repository method 호출 확인
        verify(productRepository).getProduct(productId);
    }

    @Test
    void 재고가_없는_상품을_조회시_IllegalArgumentException발생() {
        // given
        ProductService productService = new ProductService(productRepository);
        long productId = 1L;

        // Mock 설정: 상품 정보 반환
        Product product = Product.createProduct("test", 10000L, 0); // 재고 0
        given(productRepository.getProduct(productId)).willReturn(product);

        // when & then
        assertThatThrownBy(() -> productService.getProduct(productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 재고가 없습니다.");

        // verify repository method 호출 확인
        verify(productRepository).getProduct(productId);
    }

    @Test
    void 페이지와_사이즈를_입력하면_상품목록_반환() {
        // given
        ProductService productService = new ProductService(productRepository);
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        // Product 객체 리스트 생성
        Product product1 = Product.createProduct("Product1", 10000L, 10);
        ReflectionTestUtils.setField(product1, "productId", 1L);

        Product product2 = Product.createProduct("Product2", 20000L, 5);
        ReflectionTestUtils.setField(product2, "productId", 2L);


        List<Product> products = List.of(product1, product2);

// Page 객체 생성
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(page, size), products.size());

        given(productRepository.findAll(pageable)).willReturn(productPage);

        // when
        List<ProductsResponse> resultProducts = productService.getProducts(page, size);

        // then
        // 반환된 리스트 크기 검증
        assertEquals(resultProducts.size(), 2);

        // 첫 번째 상품 검증
        ProductsResponse firstProduct = resultProducts.get(0);
        assertEquals(firstProduct.productName(), "Product1");
        assertEquals(firstProduct.productPrice(), 10000L);
        assertEquals(firstProduct.productQuantity(), 10);

        // 두 번째 상품 검증
        ProductsResponse secondProduct = resultProducts.get(1);
        assertEquals(secondProduct.productName(), "Product2");
        assertEquals(secondProduct.productPrice(), 20000L);
        assertEquals(secondProduct.productQuantity(), 5);
    }

    @Test
    void ProductId와_Quantity를_입력받으면_Quantity만큼_재고차감후_반환() {
        // given
        ProductService productService = new ProductService(productRepository);
        long productId = 1L;
        int decreaseQuantity = 2;
        QuantityRequest quantityRequest = new QuantityRequest(productId, decreaseQuantity);

        // Mock 데이터 설정
        Product product = Product.createProduct("test", 10000L, 10); // 초기 재고: 10
        ReflectionTestUtils.setField(product, "productId", productId);

        // getProductWithLock 호출 시 Mock 반환
        given(productRepository.getProductWithLock(productId)).willReturn(product);

        // when
        QuantityResponse quantityResponse = productService.decreaseProductQuantity(quantityRequest);

        // then
        // 결과 검증
        assertThat(quantityResponse.productId()).isEqualTo(productId);
        assertThat(quantityResponse.productQuantity()).isEqualTo(8); // 초기 10 - 2 = 8

        // Repository 메서드 호출 검증
        verify(productRepository).getProductWithLock(productId);
        verify(productRepository).productSave(product);
    }


    @Test
    void ProductId_리스트를_입력받으면_상위_상품목록_반환() {
        // given
        ProductService productService = new ProductService(productRepository);
        List<Long> productIds = List.of(1L, 2L, 3L);

        // Mock 데이터 설정
        Product product1 = Product.createProduct("Product1", 10000L, 50);
        ReflectionTestUtils.setField(product1, "productId", 1L);

        Product product2 = Product.createProduct("Product2", 20000L, 40);
        ReflectionTestUtils.setField(product2, "productId", 2L);

        Product product3 = Product.createProduct("Product3", 15000L, 30);
        ReflectionTestUtils.setField(product3, "productId", 3L);

        List<Product> mockProducts = List.of(product1, product2, product3);

        given(productRepository.getTopProducts(productIds)).willReturn(mockProducts);

        // when
        List<ProductsResponse> resultProducts = productService.getTopProducts(productIds);

        // then
        // 반환된 리스트 크기 검증
        assertThat(resultProducts).hasSize(3);

        // 첫 번째 상품 검증
        ProductsResponse firstProduct = resultProducts.get(0);
        assertThat(firstProduct.productId()).isEqualTo(1L);
        assertThat(firstProduct.productName()).isEqualTo("Product1");
        assertThat(firstProduct.productPrice()).isEqualTo(10000L);
        assertThat(firstProduct.productQuantity()).isEqualTo(50);

        // 두 번째 상품 검증
        ProductsResponse secondProduct = resultProducts.get(1);
        assertThat(secondProduct.productId()).isEqualTo(2L);
        assertThat(secondProduct.productName()).isEqualTo("Product2");
        assertThat(secondProduct.productPrice()).isEqualTo(20000L);
        assertThat(secondProduct.productQuantity()).isEqualTo(40);

        // 세 번째 상품 검증
        ProductsResponse thirdProduct = resultProducts.get(2);
        assertThat(thirdProduct.productId()).isEqualTo(3L);
        assertThat(thirdProduct.productName()).isEqualTo("Product3");
        assertThat(thirdProduct.productPrice()).isEqualTo(15000L);
        assertThat(thirdProduct.productQuantity()).isEqualTo(30);

        // verify: repository 호출 확인
        verify(productRepository).getTopProducts(productIds);
    }
    
}