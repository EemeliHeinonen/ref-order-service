package com.eemeli.orderservice.model.discount;

public record BulkDiscount(
        String description,
        int bulkPriceInCents,
        int itemThreshold
) implements Discount {
}
