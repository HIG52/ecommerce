package kr.hhplus.be.server.product.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.product.domain.service.ProductService;
import kr.hhplus.be.server.product.domain.service.response.ProductResponse;
import kr.hhplus.be.server.product.domain.service.response.ProductsResponse;
import kr.hhplus.be.server.product.presentation.dto.ProductResponseDTO;
import kr.hhplus.be.server.product.presentation.dto.ProductsResponseDTO;
import kr.hhplus.be.server.product.presentation.usecase.ProductUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerDocs {

    private final ProductService productService;
    private final ProductUsecase productUsecase;

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

    @GetMapping("/api/products/topProduct")
    public ResponseEntity<List<ProductsResponseDTO>> getTopProduct() {

        List<ProductsResponseDTO> productsResponseDTO = productUsecase.getTopProducts();

        return ResponseEntity.status(HttpStatus.OK).body(productsResponseDTO);
    }

}