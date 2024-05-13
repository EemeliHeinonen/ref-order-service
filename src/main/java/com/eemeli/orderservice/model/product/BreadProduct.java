package com.eemeli.orderservice.model.product;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BreadProduct(
        @NotNull String name,
        @NotNull int unitPriceInCents,
        @NotNull LocalDate createdAtDate
) implements Product {
}
