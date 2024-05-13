package com.eemeli.orderservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ErrorDTO(
        int code,
        @NotNull String description,
        @NotNull List<String> reasons
) {
}
