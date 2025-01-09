package kr.hhplus.be.server.api.product.presentation.usecase;

import kr.hhplus.be.server.api.order.domain.service.OrderDetailService;
import kr.hhplus.be.server.api.product.domain.service.ProductService;
import kr.hhplus.be.server.api.product.presentation.dto.ProductsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductUsecase {

    private final ProductService productService;
    private final OrderDetailService orderDetailService;

    public ProductsResponseDTO getTopProducts() {

        //List<Long> productIds = orderDetailService.getTopProducts();

        //List<ProductsResponseDTO> products = productService.getTopProducts(productIds).stream()

        return null;
    }

}
