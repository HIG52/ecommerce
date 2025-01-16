package kr.hhplus.be.server.product.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.product.domain.service.ProductService;
import kr.hhplus.be.server.product.presentation.usecase.ProductUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/productData.sql")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductUsecase productUsecase;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("GET /api/products 요청시 상품목록을 반환한다.")
    void getProducts() throws Exception {

        int page = 0;
        int size = 10;

        mockMvc.perform(get("/api/products")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(101L))
                .andExpect(jsonPath("$[0].productName").value("Product 101"));
    }

    @Test
    @DisplayName("GET /api/products/{productId} 요청시 상품을 반환한다.")
    void getProduct() throws Exception {

        long productId = 120L;

        mockMvc.perform(get("/api/products/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Product 120"))
                .andExpect(jsonPath("$.productPrice").value(2400L))
                .andExpect(jsonPath("$.productQuantity").value(1050));

    }

    @Test
    @DisplayName("GET /api/products/topProduct 요청시 인기상품을 반환한다.")
    void getTopProduct() throws Exception {
        // 요청 및 검증
        mockMvc.perform(get("/api/products/topProduct"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(101L))
                .andExpect(jsonPath("$[0].productName").value("Product 101"))
                .andExpect(jsonPath("$[0].productPrice").value(500L))
                .andExpect(jsonPath("$[0].productQuantity").value(100))
                .andExpect(jsonPath("$[1].productId").value(102L))
                .andExpect(jsonPath("$[1].productName").value("Product 102"))
                .andExpect(jsonPath("$[1].productPrice").value(600L))
                .andExpect(jsonPath("$[1].productQuantity").value(150));
    }
}