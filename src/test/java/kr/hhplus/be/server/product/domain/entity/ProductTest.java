package kr.hhplus.be.server.product.domain.entity;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.createProduct("Test Product", 1000L, 10);
        ReflectionTestUtils.setField(product, "productId", 1L);
    }

    @Test
    @DisplayName("상품 생성 후 필드 값이 정상적으로 초기화된다")
    void createProduct_Success() {
        // then
        assertThat(product.getProductId()).isEqualTo(1L);
        assertThat(product.getProductName()).isEqualTo("Test Product");
        assertThat(product.getProductPrice()).isEqualTo(1000L);
        assertThat(product.getProductQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("상품 수량 감소 - 정상적으로 감소한다")
    void decreaseProductQuantity_Success() {
        // given
        int decreaseAmount = 5;

        // when
        product.decreaseProductQuantity(decreaseAmount);

        // then
        assertThat(product.getProductQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("상품 수량 감소 - 수량 부족 시 예외를 발생한다")
    void decreaseProductQuantity_InsufficientQuantity() {
        // given
        int decreaseAmount = 15;

        // when & then
        assertThatThrownBy(() -> product.decreaseProductQuantity(decreaseAmount))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.STOCK_QUANTITY_NOT_ENOUGH.getMessage());
    }

}