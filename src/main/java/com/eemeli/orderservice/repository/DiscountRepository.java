package com.eemeli.orderservice.repository;

import com.eemeli.orderservice.model.discount.*;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DiscountRepository {
    // placeholder for a DB
    private final HashMap<DiscountType, Discount> discountsByType = new HashMap<>() {{
        put(DiscountType.BEER_SIX_PACK, new BulkDiscount("Six pack of beer for 2€", 200, 6));
        put(DiscountType.VEG_PERCENTAGE_500_G, new PercentageDiscount("10% discount for more than 500g of vegetables", 0.9));
        put(DiscountType.VEG_PERCENTAGE_100_G, new PercentageDiscount("7% discount for 100g-500g of vegetables", 0.93));
        put(DiscountType.VEG_PERCENTAGE_1_G, new PercentageDiscount("5% discount for 1g-99g of vegetables", 0.95));
        put(DiscountType.BREAD_BUY_1_TAKE_2, new BuyXTakeYDiscount("Buy 1 take 2 for three-day-old bread", 2));
        put(DiscountType.BREAD_BUY_1_TAKE_3, new BuyXTakeYDiscount("Buy 1 take 3 for six-day-old bread", 3));
    }};

    private Map<DiscountType, Discount> immutableDiscountsByType = Collections.unmodifiableMap(discountsByType);


    public @NotNull Map<DiscountType, Discount> getDiscountMap() {
        return immutableDiscountsByType;
    }

    public @NotNull List<Discount> getAllDiscounts() {
        return discountsByType.values().stream().toList();
    }

    public @Nullable Discount getDiscountByType(@NotNull DiscountType type) {
        return discountsByType.get(type);
    }
}
