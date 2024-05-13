package com.eemeli.orderservice.model.product;

import jakarta.validation.constraints.NotNull;

public record VegetableProduct(
        @NotNull String name,
        @NotNull int unitPriceInCents,
        @NotNull int weightInGrams
) implements Product {
}
