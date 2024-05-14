package com.eemeli.orderservice.service;

import com.eemeli.orderservice.dto.ProductDTO;
import com.eemeli.orderservice.repository.ProductRepository;
import com.eemeli.orderservice.utility.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProductDTOs() {
        return productRepository
                .getAllProducts()
                .stream()
                .map(ProductMapper::mapProductToProductDTO)
                .toList();
    }
}
