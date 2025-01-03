package kr.hhplus.be.server.api.controller;

import kr.hhplus.be.server.api.dto.ProductResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        
        //예제 데이터
        List<ProductResponseDTO> responseList = new ArrayList<>();
        ProductResponseDTO product1 = new ProductResponseDTO();
        product1.setProductId(1);
        product1.setProductName("상품명1");
        product1.setProductPrice(10000);

        ProductResponseDTO product2 = new ProductResponseDTO();
        product2.setProductId(2);
        product2.setProductName("상품명2");
        product2.setProductPrice(20000);

        responseList.add(product1);
        responseList.add(product2);

        // 리스트 반환
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/api/products/{productId}")
    public ResponseEntity<ProductResponseDTO> getProduct(
            @PathVariable(name = "productId") int productId) {

        ProductResponseDTO response = new ProductResponseDTO();

        response.setProductId(productId);
        response.setProductName("상품명");
        response.setProductPrice(10000);
        response.setProductQuantity(10);

        if(productId <= 0) {
            response.setMessage("상품이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(response.getProductQuantity() <= 0) {
            response.setMessage("상품이 품절되었습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }



        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/products/topProduct")
    public ResponseEntity<List<ProductResponseDTO>> getTopProduct() {

        //예제 데이터
        List<ProductResponseDTO> responseList = new ArrayList<>();
        ProductResponseDTO product1 = new ProductResponseDTO();
        product1.setProductId(1);
        product1.setProductName("상품명1");
        product1.setProductPrice(10000);

        ProductResponseDTO product2 = new ProductResponseDTO();
        product2.setProductId(2);
        product2.setProductName("상품명2");
        product2.setProductPrice(20000);

        ProductResponseDTO product3 = new ProductResponseDTO();
        product3.setProductId(3);
        product3.setProductName("상품명3");
        product3.setProductPrice(30000);

        ProductResponseDTO product4 = new ProductResponseDTO();
        product4.setProductId(3);
        product4.setProductName("상품명3");
        product4.setProductPrice(30000);

        ProductResponseDTO product5 = new ProductResponseDTO();
        product5.setProductId(3);
        product5.setProductName("상품명3");
        product5.setProductPrice(30000);

        responseList.add(product1);
        responseList.add(product2);
        responseList.add(product3);
        responseList.add(product4);
        responseList.add(product5);



        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

}