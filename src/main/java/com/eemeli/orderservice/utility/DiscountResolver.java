package com.eemeli.orderservice.utility;

import com.eemeli.orderservice.model.discount.*;
import com.eemeli.orderservice.model.product.BeerProduct;
import com.eemeli.orderservice.model.product.BreadProduct;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class DiscountResolver {
    public static long resolveDiscountedBeerPrice(
            int quantity,
            BeerProduct beer,
            Discount discount) {
        if (discount instanceof BulkDiscount bulkDiscount) {
            var bulkUnitCount = quantity / bulkDiscount.itemThreshold();
            var singleBeerCount = quantity % bulkDiscount.itemThreshold();

            var bulkUnitsPrice = bulkUnitCount * bulkDiscount.bulkPriceInCents();
            var singlesPrice = singleBeerCount * beer.unitPriceInCents();

            return bulkUnitsPrice + singlesPrice;
        }

        return (long) quantity * beer.unitPriceInCents();
    }

    public static long resolveDiscountedBreadPrice(int quantity, int unitPrice, BreadProduct bread, Map<DiscountType, Discount> discountsByType) {
        var breadAgeDays = ChronoUnit.DAYS.between(bread.createdAtDate(), LocalDate.now());

        if (breadAgeDays == 6) {
            var discount = discountsByType.get(DiscountType.BREAD_BUY_1_TAKE_3);
            if (discount instanceof BuyXTakeYDiscount buy1Take3Discount) {
                return calculateBuyXTakeYDiscountedPrice(quantity, unitPrice, buy1Take3Discount);
            }
        } else if (breadAgeDays >= 3) {
            var discount = discountsByType.get(DiscountType.BREAD_BUY_1_TAKE_2);
            if (discount instanceof BuyXTakeYDiscount buy1Take2Discount) {
                return calculateBuyXTakeYDiscountedPrice(quantity, unitPrice, buy1Take2Discount);
            }
        }

        return (long) quantity * unitPrice;
    }

    public static long resolveDiscountedVegetablesPrice(int weightInGrams, int unitPrice, Discount discount) {
        var priceWithoutDiscount = weightInGrams * unitPrice / 100;

        if (discount instanceof PercentageDiscount percentageDiscount) {
            return (long) (priceWithoutDiscount * percentageDiscount.multiplier());
        }

        return priceWithoutDiscount;
    }

    private static long calculateBuyXTakeYDiscountedPrice(int quantity, int unitPrice, BuyXTakeYDiscount discount) {
        int effectiveGroups = (int) Math.ceil((double) quantity / discount.maxDiscountItems());
        return (long) effectiveGroups * unitPrice;
    }
}
