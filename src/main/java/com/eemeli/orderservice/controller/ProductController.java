package com.eemeli.orderservice.controller;

import com.eemeli.orderservice.controller.resources.ProductResources;
import com.eemeli.orderservice.dto.ProductDTO;
import com.eemeli.orderservice.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController implements ProductResources {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        var products = productService.getAllProductDTOs();
        return ResponseEntity.ok()
                .body(products);
    }
}
