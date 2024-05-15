package com.eemeli.orderservice;

import com.eemeli.orderservice.dto.ProductDTO;
import com.eemeli.orderservice.dto.ProductDTOCategory;
import com.eemeli.orderservice.model.product.BreadProduct;

import java.time.LocalDate;

public class TestHelpers {
    public static BreadProduct buildBreadWithAgeInDays(int days) {
        int unitPriceInCents = 100;
        return new BreadProduct(
                "Test Bread",
                unitPriceInCents,
                LocalDate.now().minusDays(days)
        );
    }

    public static ProductDTO buildBreadProductDTOWithAgeInDays(int days) {
        return new ProductDTO(
                "Bread",
                ProductDTOCategory.BREAD,
                100,
                null,
                LocalDate.now().minusDays(days)
        );
    }

    public static ProductDTO buildBeerProductDTOWithName(String name) {
        return new ProductDTO(
                name,
                ProductDTOCategory.BEER,
                50,
                null,
                null
        );
    }

    public static ProductDTO buildVegetableProductDTOWithWeight(int weightInGrams) {
        int unitPriceInCents = 100;
        return new ProductDTO(
                "Vegetables",
                ProductDTOCategory.VEGETABLE,
                unitPriceInCents,
                weightInGrams,
                null
        );
    }
}
