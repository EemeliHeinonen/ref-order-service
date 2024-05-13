package com.eemeli.orderservice.model.product;

import jakarta.validation.constraints.NotNull;

public record BeerProduct(
        @NotNull String name,
        @NotNull int unitPriceInCents
) implements Product {
}
