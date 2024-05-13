package com.eemeli.orderservice.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProductDTO(
        @NotNull String name,
        int unitPriceInCents,
        @Nullable Integer weightInGrams,
        @Nullable LocalDate createdAtDate
        )  {
}
