package com.eemeli.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ErrorDTO(
        int code,
        @NotBlank
        String description,
        @NotEmpty
        List<String> reasons
) {
}
