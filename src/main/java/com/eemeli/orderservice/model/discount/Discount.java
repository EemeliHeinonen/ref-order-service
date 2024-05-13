package com.eemeli.orderservice.model.discount;

public sealed interface Discount permits BulkDiscount, PercentageDiscount, BuyXTakeYDiscount {
    String description();
}