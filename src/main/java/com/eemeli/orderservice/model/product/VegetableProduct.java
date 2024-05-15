package com.eemeli.orderservice.model.product;

import jakarta.validation.constraints.NotNull;

public record VegetableProduct(
        @NotNull String name,
        @NotNull int unitPriceInCents,
        @NotNull int weightInGrams
) implements Product {
    @Override
    public long originalPriceInCents(int ignored) {
        return (long) weightInGrams * unitPriceInCents / 100;
    }
}
