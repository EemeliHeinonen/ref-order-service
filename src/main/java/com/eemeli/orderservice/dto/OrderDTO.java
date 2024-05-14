package com.eemeli.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderDTO(
        @Valid
        @NotNull
        @NotEmpty
        List<OrderItemDTO> orderItems) {
}
