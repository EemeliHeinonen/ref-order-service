package com.eemeli.orderservice;

import com.eemeli.orderservice.model.discount.Discount;
import com.eemeli.orderservice.model.discount.PercentageDiscount;
import org.junit.jupiter.api.Test;

import static com.eemeli.orderservice.utility.DiscountResolver.resolveDiscountedVegetablesPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountResolverVegetableTest {
    @Test
    void testResolveDiscountedVegetablesPriceWithPercentageDiscount() {
        // Arrange
        int weightInGrams = 500;
        int unitPrice = 200; // price per 100 grams
        double tenPercentDiscountMultiplier = 0.9;
        Discount discount = new PercentageDiscount("10% discount", tenPercentDiscountMultiplier);

        // Act
        long discountedPrice = resolveDiscountedVegetablesPrice(weightInGrams, unitPrice, discount);

        // Assert
        // Expected calculation: 500 grams at 200 cents per 100 grams with 10% discount
        // (500 * 200 / 100) * 0.9 = 1000 * 0.9 = 900 cents
        assertEquals(900, discountedPrice);
    }

    @Test
    void testResolveDiscountedVegetablesPriceWithoutDiscount() {
        // Arrange
        int weightInGrams = 500;
        int unitPrice = 200; // price per 100 grams
        Discount discount = null;

        // Act
        long discountedPrice = resolveDiscountedVegetablesPrice(weightInGrams, unitPrice, discount);

        // Assert
        // Expected calculation: 500 grams at 200 cents per 100 grams
        // 500 * 200 / 100 = 1000 cents
        assertEquals(1000, discountedPrice);
    }

    @Test
    void testResolveDiscountedVegetablesPriceWithDifferentPercentageDiscount() {
        // Arrange
        int weightInGrams = 700;
        int unitPrice = 150; // price per 100 grams
        double twentyPercentDiscountMultiplier = 0.8;
        Discount discount = new PercentageDiscount("20% discount", twentyPercentDiscountMultiplier);

        // Act
        long discountedPrice = resolveDiscountedVegetablesPrice(weightInGrams, unitPrice, discount);

        // Assert
        // Expected calculation: 700 grams at 150 cents per 100 grams with 20% discount
        // (700 * 150 / 100) * 0.8 = 1050 * 0.8 = 840 cents
        assertEquals(840, discountedPrice);
    }

    @Test
    void testResolveDiscountedVegetablesPriceWithoutApplicableDiscount() {
        // Arrange
        int weightInGrams = 300;
        int unitPrice = 100; // price per 100 grams
        double fiftyPercentDiscountModifier = 0.5;
        Discount discount = new PercentageDiscount("50% discount", fiftyPercentDiscountModifier);

        // Act
        long discountedPrice = resolveDiscountedVegetablesPrice(weightInGrams, unitPrice, discount);

        // Assert
        // Expected calculation: 300 grams at 100 cents per 100 grams with 50% discount
        // (300 * 100 / 100) * 0.5 = 300 * 0.5 = 150 cents
        assertEquals(150, discountedPrice);
    }
}
