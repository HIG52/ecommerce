package kr.hhplus.be.server.api.product.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.api.balance.presentation.dto.BalanceResponseDTO;
import kr.hhplus.be.server.api.coupon.domain.service.response.CouponsResponse;
import kr.hhplus.be.server.api.coupon.presentation.dto.CouponsResponseDTO;
import kr.hhplus.be.server.api.product.domain.service.ProductService;
import kr.hhplus.be.server.api.product.domain.service.response.ProductResponse;
import kr.hhplus.be.server.api.product.domain.service.response.ProductsResponse;
import kr.hhplus.be.server.api.product.presentation.dto.ProductResponseDTO;
import kr.hhplus.be.server.api.product.presentation.dto.ProductsResponseDTO;
import kr.hhplus.be.server.api.product.presentation.usecase.ProductUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product API", description = "상품 관리 API")
public class ProductController {

    private final ProductService productService;
    private final ProductUsecase productUsecase;

    @Operation(
            summary = "상품목록 조회",
            description = "상품목록을 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                            content = @Content(schema = @Schema(implementation = ProductsResponseDTO.class)))
            }
    )
    @GetMapping("/api/products")
    public ResponseEntity<List<ProductsResponseDTO>> getProducts(
            @Valid @RequestParam(defaultValue = "0") int page,
            @Valid @RequestParam(defaultValue = "10") int size
    ) {

        List<ProductsResponse> products = productService.getProducts(page, size);

        List<ProductsResponseDTO> resultProducts = products.stream()
                .map(product -> new ProductsResponseDTO(
                        product.productId(),
                        product.productName(),
                        product.productPrice(),
                        product.productQuantity()
                ))
                .toList();

        // 리스트 반환
        return ResponseEntity.status(HttpStatus.OK).body(resultProducts);
    }

    @Operation(
            summary = "상품상세 조회",
            description = "상품상세를 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공",
                            content = @Content(schema = @Schema(implementation = ProductResponseDTO.class)))
            }
    )
    @GetMapping("/api/products/{productId}")
    public ResponseEntity<ProductResponseDTO> getProduct(
            @PathVariable(name = "productId") int productId) {

        ProductResponse product = productService.getProduct(productId);

        ProductResponseDTO resultProduct = new ProductResponseDTO(
                product.productName(),
                product.productPrice(),
                product.productQuantity()
        );

        return ResponseEntity.status(HttpStatus.OK).body(resultProduct);
    }

    @Operation(
            summary = "상위상품 조회",
            description = "많이팔린 상위3개 상품을 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "상위상품 조회 성공",
                            content = @Content(schema = @Schema(implementation = ProductsResponseDTO.class)))
            }
    )
    @GetMapping("/api/products/topProduct")
    public ResponseEntity<List<ProductsResponseDTO>> getTopProduct() {

        productUsecase.getTopProducts();

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}