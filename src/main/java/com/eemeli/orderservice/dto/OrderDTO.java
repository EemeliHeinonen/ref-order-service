package com.eemeli.orderservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderDTO(@NotNull List<OrderItemDTO> orderItems) {
}
