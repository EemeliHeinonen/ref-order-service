package com.eemeli.orderservice.dto;

import com.eemeli.orderservice.validation.ValidOrderItemDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@ValidOrderItemDTO
public record OrderItemDTO(
        @NotNull
        @Valid
        ProductDTO product,
        @Positive
        int quantity
) {
}
