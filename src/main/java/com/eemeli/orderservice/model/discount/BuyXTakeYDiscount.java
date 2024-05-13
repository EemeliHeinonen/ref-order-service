package com.eemeli.orderservice.model.discount;

public record BuyXTakeYDiscount(
        String description,
        int maxDiscountItems
) implements Discount {
}
