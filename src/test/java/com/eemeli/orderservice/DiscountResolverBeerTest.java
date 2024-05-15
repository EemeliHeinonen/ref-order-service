package com.eemeli.orderservice;

import com.eemeli.orderservice.model.discount.BulkDiscount;
import com.eemeli.orderservice.model.discount.Discount;
import com.eemeli.orderservice.model.product.BeerProduct;
import org.junit.jupiter.api.Test;

import static com.eemeli.orderservice.utility.DiscountResolver.resolveDiscountedBeerPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountResolverBeerTest {
    int unitPriceInCents = 100;
    int bulkPriceInCents = 200;
    BeerProduct beer = new BeerProduct("Test Beer", unitPriceInCents);
    BulkDiscount bulkDiscount = new BulkDiscount("Test Bulk Discount", bulkPriceInCents, 6);
    // resolveDiscountedBeerPrice
    @Test
    void testResolveDiscountedBeerPriceWithBulkDiscount() {
        // Arrange
        int quantity = 10;

        // Act
        long discountedPrice = resolveDiscountedBeerPrice(quantity, beer, bulkDiscount);

        // Assert
        // Expected calculation: 10 beers with 6 at bulk price of 200 cents and 4 at unit price of 100 cents
        // (1 bulk unit * 200 cents) + (4 single units * 100 cents) = 200 + 400 = 600 cents
        assertEquals(600, discountedPrice);
    }

    @Test
    void testResolveDiscountedBeerPriceWithoutDiscount() {
        // Arrange
        Discount discount = null;
        int quantity = 10;

        // Act
        long discountedPrice = resolveDiscountedBeerPrice(quantity, beer, discount);

        // Assert
        // Expected calculation: 10 beers at unit price of 100 cents
        // 10 * 100 = 1000 cents
        assertEquals(1000, discountedPrice);
    }

    @Test
    void testResolveDiscountedBeerPriceWithIncompleteBulkDiscount() {
        // Arrange
        int quantity = 5;

        // Act
        long discountedPrice = resolveDiscountedBeerPrice(quantity, beer, bulkDiscount);

        // Assert
        // Expected calculation: 5 beers at unit price of 100 cents
        // 5 * 100 = 500 cents
        assertEquals(500, discountedPrice);
    }

    @Test
    void testResolveDiscountedBeerPriceWithMultipleBulkUnits() {
        // Arrange
        int quantity = 12;

        // Act
        long discountedPrice = resolveDiscountedBeerPrice(quantity, beer, bulkDiscount);

        // Assert
        // Expected calculation: 12 beers with 2 bulk units at 200 cents each
        // (2 bulk units * 200 cents) = 400 cents
        assertEquals(400, discountedPrice);
    }
}
