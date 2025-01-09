package kr.hhplus.be.server.api.product.presentation.usecase;

import kr.hhplus.be.server.api.order.domain.service.OrderDetailService;
import kr.hhplus.be.server.api.order.domain.service.response.OrderDetailsResponse;
import kr.hhplus.be.server.api.product.domain.service.ProductService;
import kr.hhplus.be.server.api.product.domain.service.response.ProductsResponse;
import kr.hhplus.be.server.api.product.presentation.dto.ProductResponseDTO;
import kr.hhplus.be.server.api.product.presentation.dto.ProductsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductUsecase {

    private final ProductService productService;
    private final OrderDetailService orderDetailService;

    public List<ProductsResponseDTO> getTopProducts() {

        /*List<OrderDetailsResponse> productIds = orderDetailService.getTopOrderDetails();*/
        List<Long> productIds = orderDetailService.getTopOrderDetails().stream()
                .map(OrderDetailsResponse::productId) // productId만 추출
                .collect(Collectors.toList());

        List<ProductsResponse> products = productService.getTopProducts(productIds);

        // productIds 순서대로 정렬
        Map<Long, ProductsResponse> productMap = products.stream()
                .collect(Collectors.toMap(ProductsResponse::productId, product -> product));

        List<ProductsResponse> sortedProducts = productIds.stream()
                .map(productMap::get)
                .toList();

        return sortedProducts.stream()
                .map(product -> new ProductsResponseDTO(
                        product.productId(),
                        product.productName(),
                        product.productPrice(),
                        product.productQuantity()
                ))
                .collect(Collectors.toList());
    }

}
