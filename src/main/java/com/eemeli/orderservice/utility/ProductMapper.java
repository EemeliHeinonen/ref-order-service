package com.eemeli.orderservice.utility;

import com.eemeli.orderservice.dto.ProductDTO;
import com.eemeli.orderservice.model.product.*;
import jakarta.validation.constraints.NotNull;

public class ProductMapper {
    public static Product mapProductDTOToProduct(@NotNull ProductDTO productDTO) {
        if (productDTO.createdAtDate() != null) {
            return new BreadProduct(productDTO.name(), productDTO.unitPriceInCents(), productDTO.createdAtDate());
        } else if (productDTO.weightInGrams() != null) {
            return new VegetableProduct(productDTO.name(), productDTO.unitPriceInCents(), productDTO.weightInGrams());
        }

        return new BeerProduct(productDTO.name(), productDTO.unitPriceInCents());
    }
}
