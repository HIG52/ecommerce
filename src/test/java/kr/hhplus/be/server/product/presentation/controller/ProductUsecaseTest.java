package kr.hhplus.be.server.product.presentation.controller;

import kr.hhplus.be.server.product.presentation.dto.ProductsResponseDTO;
import org.junit.jupiter.api.DisplayName;
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
public class ProductUsecaseTest {

    @Autowired
    private ProductController productController;

    @Test
    @DisplayName("상위 상품 5개 조회")
    void findTopProducts() {
        long startTime = System.nanoTime();

        // When
        ResponseEntity<List<ProductsResponseDTO>> response = productController.getTopProduct();
        long endTime = System.nanoTime();
        // 실행 시간 계산 (밀리초 단위로 변환)
        long durationInNano = endTime - startTime;
        double durationInMs = durationInNano / 1_000_000.0;
        System.out.println("getTopProduct() 실행 시간: " + durationInMs + " ms");

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0); // 응답 리스트가 비어 있지 않아야 함

        List<ProductsResponseDTO> products = response.getBody();

        // 검증: 데이터가 예상대로 정렬되어 있는지 확인
        assertThat(products.get(0).productId()).isEqualTo(101L); // 첫 번째 상품 ID (가장 많이 팔린 상품)
        assertThat(products.get(1).productId()).isEqualTo(102L); // 두 번째 상품 ID
        assertThat(products.get(2).productId()).isEqualTo(103L); // 세 번째 상품 ID
        assertThat(products.get(3).productId()).isEqualTo(104L); // 네 번째 상품 ID
        assertThat(products.get(4).productId()).isEqualTo(105L); // 다섯 번째 상품 ID
    }

}
