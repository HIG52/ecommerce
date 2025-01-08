package kr.hhplus.be.server.api.product.domain.service;

import kr.hhplus.be.server.api.product.domain.entity.Product;
import kr.hhplus.be.server.api.product.domain.repository.ProductRepository;
import kr.hhplus.be.server.api.product.presentation.dto.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDTO getProduct(long productId) {

        Product product = productRepository.getProduct(productId);

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProductName(product.getProductName());
        productResponseDTO.setProductPrice(product.getProductPrice());
        productResponseDTO.setProductQuantity(product.getProductQuantity());

        return productResponseDTO;
    }

    public Page<ProductResponseDTO> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(product -> {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setProductName(product.getProductName());
            dto.setProductPrice(product.getProductPrice());
            dto.setProductQuantity(product.getProductQuantity());
            return dto;
        });
    }

    // TODO : 전달객체 수정 예정
    public List<ProductResponseDTO> updateProductQuantity(List<ProductResponseDTO> productResponseDTOList) {

        for (ProductResponseDTO productResponseDTO : productResponseDTOList) {
            Product product = productRepository.getProduct(productResponseDTO.getProductId());
            product.updateProductQuantity(productResponseDTO.getProductQuantity());
            productRepository.productSave(product);
        }
        return productResponseDTOList;
    }

}
