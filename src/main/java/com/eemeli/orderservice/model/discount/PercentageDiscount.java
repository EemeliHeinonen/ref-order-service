package com.eemeli.orderservice.model.discount;

public record PercentageDiscount(
        String description,
        double multiplier
) implements Discount {
}
