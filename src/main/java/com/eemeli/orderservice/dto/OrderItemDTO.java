package com.eemeli.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemDTO(
        @NotNull
        @Valid
        ProductDTO product,
        @Positive
        int quantity
) {
}
