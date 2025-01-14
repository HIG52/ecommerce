package kr.hhplus.be.server.product.presentation.controller;

import kr.hhplus.be.server.product.presentation.controller.ProductController;
import kr.hhplus.be.server.product.presentation.dto.ProductsResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/productData.sql")
@SpringBootTest
@ActiveProfiles("test")
public class ProductIntegrationTest {

    @Autowired
    private ProductController productController;

    @Test
    void 상위상품_조회() {
        // When
        ResponseEntity<List<ProductsResponseDTO>> response = productController.getTopProduct();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0); // 응답 리스트가 비어 있지 않아야 함

        List<ProductsResponseDTO> products = response.getBody();

        // 검증: 데이터가 예상대로 정렬되어 있는지 확인
        assertThat(products.get(0).productId()).isEqualTo(108L); // 첫 번째 상품 ID (가장 많이 팔린 상품)
        assertThat(products.get(1).productId()).isEqualTo(109L); // 두 번째 상품 ID
        assertThat(products.get(2).productId()).isEqualTo(107L); // 세 번째 상품 ID
    }

}
