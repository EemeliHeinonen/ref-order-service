package com.eemeli.orderservice;

import com.eemeli.orderservice.model.discount.BuyXTakeYDiscount;
import com.eemeli.orderservice.model.discount.Discount;
import com.eemeli.orderservice.model.discount.DiscountType;
import com.eemeli.orderservice.model.product.BreadProduct;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.eemeli.orderservice.TestHelpers.buildBreadWithAgeInDays;
import static com.eemeli.orderservice.utility.DiscountResolver.resolveDiscountedBreadPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountResolverBreadTest {
    int unitPriceInCents = 100;
    private final Map<DiscountType, Discount> discountsByType = new HashMap<>() {{
        put(DiscountType.BREAD_BUY_1_TAKE_2, new BuyXTakeYDiscount("Buy 1 take 2 for three-day-old bread", 2));
        put(DiscountType.BREAD_BUY_1_TAKE_3, new BuyXTakeYDiscount("Buy 1 take 3 for six-day-old bread", 3));
    }};

    @Test
    void testResolveDiscountedBreadPriceWithSixDayOldBread() {
        // Arrange
        int quantity = 7;
        BreadProduct sixDayOldBread = buildBreadWithAgeInDays(6);

        // Act
        long discountedPrice = resolveDiscountedBreadPrice(quantity, unitPriceInCents, sixDayOldBread, discountsByType);

        // Assert
        // Expected calculation: 7 breads with buy 1 take 3 discount (max 3 per 1)
        // (3 groups * 100 cents) = 300 cents
        assertEquals(300, discountedPrice);
    }

    @Test
    void testResolveDiscountedBreadPriceWithThreeDayOldBread() {
        // Arrange
        int quantity = 4;
        BreadProduct threeDayOldBread = buildBreadWithAgeInDays(3);

        // Act
        long discountedPrice = resolveDiscountedBreadPrice(quantity, unitPriceInCents, threeDayOldBread, discountsByType);

        // Assert
        // Expected calculation: 4 breads with buy 1 take 2 discount (max 2 per 1)
        // (2 groups * 100 cents) = 200 cents
        assertEquals(200, discountedPrice);
    }

    @Test
    void testResolveDiscountedBreadPriceWithFreshBread() {
        // Arrange
        int quantity = 5;
        BreadProduct freshBread = buildBreadWithAgeInDays(0);

        // Act
        long discountedPrice = resolveDiscountedBreadPrice(quantity, unitPriceInCents, freshBread, discountsByType);

        // Assert
        // Expected calculation: 5 breads at unit price of 100 cents
        // 5 * 100 = 500 cents
        assertEquals(500, discountedPrice);
    }

    @Test
    void testResolveDiscountedBreadPriceWithOldBreadButNoApplicableDiscount() {
        // Arrange
        int quantity = 2;
        BreadProduct tooOldBread = buildBreadWithAgeInDays(7);

        // Act
        long discountedPrice = resolveDiscountedBreadPrice(quantity, unitPriceInCents, tooOldBread, discountsByType);

        // Assert
        // Expected calculation: 2 breads at unit price of 100 cents
        // 2 * 100 = 200 cents
        assertEquals(200, discountedPrice);
    }

}
