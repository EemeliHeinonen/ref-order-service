package com.eemeli.orderservice.model.dto;

import com.eemeli.orderservice.model.product.ProductDTO;
import jakarta.validation.constraints.NotNull;

public record OrderItemDTO(
        @NotNull ProductDTO product,
        int quantity
) {
}
