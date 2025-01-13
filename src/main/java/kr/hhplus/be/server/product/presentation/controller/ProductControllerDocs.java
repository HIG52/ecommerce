package kr.hhplus.be.server.product.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.product.presentation.dto.ProductResponseDTO;
import kr.hhplus.be.server.product.presentation.dto.ProductsResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Product API", description = "상품 관리 API")
public interface ProductControllerDocs {

    @Operation(
            summary = "상품목록 조회",
            description = "상품목록을 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                            content = @Content(schema = @Schema(implementation = ProductsResponseDTO.class)))
            }
    )
    ResponseEntity<List<ProductsResponseDTO>> getProducts(
            @Valid @RequestParam(defaultValue = "0") int page,
            @Valid @RequestParam(defaultValue = "10") int size
    );

    @Operation(
            summary = "상품상세 조회",
            description = "상품상세를 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공",
                            content = @Content(schema = @Schema(implementation = ProductResponseDTO.class)))
            }
    )
    ResponseEntity<ProductResponseDTO> getProduct(
            @PathVariable(name = "productId") int productId);

    @Operation(
            summary = "상위상품 조회",
            description = "많이팔린 상위3개 상품을 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "상위상품 조회 성공",
                            content = @Content(schema = @Schema(implementation = ProductsResponseDTO.class)))
            }
    )
    ResponseEntity<List<ProductsResponseDTO>> getTopProduct();

}
