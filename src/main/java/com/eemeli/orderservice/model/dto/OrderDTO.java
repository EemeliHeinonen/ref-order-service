package com.eemeli.orderservice.model.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderDTO(@NotNull List<OrderItemDTO> orderItems) {
}
