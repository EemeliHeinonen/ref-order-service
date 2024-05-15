package com.eemeli.orderservice.utility;

import com.eemeli.orderservice.dto.ProductDTO;
import com.eemeli.orderservice.dto.ProductDTOCategory;
import com.eemeli.orderservice.model.product.*;
import jakarta.validation.constraints.NotNull;

public class ProductMapper {

public static Product mapProductDTOToProduct(@NotNull ProductDTO productDTO) {
    return switch (productDTO.category()) {
        case BEER -> new BeerProduct(productDTO.name(), productDTO.unitPriceInCents());
        case BREAD -> new BreadProduct(productDTO.name(), productDTO.unitPriceInCents(), productDTO.createdAtDate());
        case VEGETABLE -> new VegetableProduct(productDTO.name(), productDTO.unitPriceInCents(), productDTO.weightInGrams());
    };
}

    public static ProductDTO mapProductToProductDTO(@NotNull Product product) {
        var weightInGrams = product instanceof VegetableProduct vegetableProduct
                ? vegetableProduct.weightInGrams()
                : null;

        var createdAtDate = product instanceof BreadProduct breadProduct
                ? breadProduct.createdAtDate()
                : null;

        var category = switch (product) {
            case BeerProduct beer -> ProductDTOCategory.BEER;
            case BreadProduct bread -> ProductDTOCategory.BREAD;
            case VegetableProduct veg -> ProductDTOCategory.VEGETABLE;
        };

        return new ProductDTO(
                product.name(),
                category,
                product.unitPriceInCents(),
                weightInGrams,
                createdAtDate
        );
    }
}
