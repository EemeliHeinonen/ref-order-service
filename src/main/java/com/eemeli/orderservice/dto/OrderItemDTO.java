package com.eemeli.orderservice.dto;

import jakarta.validation.constraints.NotNull;

public record OrderItemDTO(
        @NotNull ProductDTO product,
        int quantity
) {
}
