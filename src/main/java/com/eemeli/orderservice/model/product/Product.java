package com.eemeli.orderservice.model.product;

import jakarta.validation.constraints.NotNull;

public sealed interface Product permits BeerProduct, BreadProduct, VegetableProduct {
    @NotNull String name();
    @NotNull int unitPriceInCents();
    default long originalPriceInCents(int quantity) {
        return (long) unitPriceInCents() * quantity;
    }
}
