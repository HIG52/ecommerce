package kr.hhplus.be.server.product.domain.service.service;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.product.domain.entity.Product;
import kr.hhplus.be.server.product.domain.repository.ProductRepository;
import kr.hhplus.be.server.product.domain.service.ProductService;
import kr.hhplus.be.server.product.domain.service.request.QuantityRequest;
import kr.hhplus.be.server.product.domain.service.response.ProductResponse;
import kr.hhplus.be.server.product.domain.service.response.ProductsResponse;
import kr.hhplus.be.server.product.domain.service.response.QuantityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.createProduct("Test Product", 10000L, 10);
        ReflectionTestUtils.setField(product, "productId", 1L);
    }

    @Test
    @DisplayName("상품 ID를 입력하면 ProductResponse를 반환한다")
    void getProduct_Success() {
        // given
        given(productRepository.getProduct(1L)).willReturn(product);

        // when
        ProductResponse response = productService.getProduct(1L);

        // then
        assertThat(response.productName()).isEqualTo("Test Product");
        assertThat(response.productPrice()).isEqualTo(10000L);
        assertThat(response.productQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID를 입력하면 CustomExceptionHandler를 반환한다")
    void getProduct_NotFound() {
        // given
        given(productRepository.getProduct(1L)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> productService.getProduct(1L))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("페이지와 사이즈를 입력하면 상품 목록을 반환한다")
    void getProducts_Success() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products);

        given(productRepository.findAll(pageRequest)).willReturn(productPage);

        // when
        List<ProductsResponse> responses = productService.getProducts(0, 10);

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).productName()).isEqualTo("Test Product");
        assertThat(responses.get(0).productPrice()).isEqualTo(10000L);
        assertThat(responses.get(0).productQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("상품 ID와 수량을 입력하면 수량만큼 재고를 차감한 후 QuantityResponse를 반환한다")
    void decreaseProductQuantity_Success() {
        // given
        QuantityRequest request = new QuantityRequest(1L, 2);
        given(productRepository.getProductWithLock(1L)).willReturn(product);

        // when
        QuantityResponse response = productService.decreaseProductQuantity(request);

        // then
        assertThat(response.productId()).isEqualTo(1L);
        assertThat(response.productQuantity()).isEqualTo(8);

        verify(productRepository).getProductWithLock(1L);
        verify(productRepository).productSave(product);
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID로 수량 차감을 요청하면 CustomExceptionHandler를 반환한다")
    void decreaseProductQuantity_NotFound() {
        // given
        QuantityRequest request = new QuantityRequest(1L, 2);
        given(productRepository.getProductWithLock(1L)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> productService.decreaseProductQuantity(request))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("상품 ID 리스트를 입력하면 해당 상품 목록을 반환한다")
    void getTopProducts_Success() {
        // given
        List<Long> productIds = List.of(1L);
        given(productRepository.getTopProducts(productIds)).willReturn(List.of(product));

        // when
        List<ProductsResponse> responses = productService.getTopProducts(productIds);

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).productId()).isEqualTo(1L);
        assertThat(responses.get(0).productName()).isEqualTo("Test Product");
        assertThat(responses.get(0).productPrice()).isEqualTo(10000L);
        assertThat(responses.get(0).productQuantity()).isEqualTo(10);

        verify(productRepository).getTopProducts(productIds);
    }
    
}