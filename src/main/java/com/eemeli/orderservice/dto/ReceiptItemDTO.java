package com.eemeli.orderservice.dto;

import com.eemeli.orderservice.model.discount.Discount;
import com.eemeli.orderservice.model.discount.DiscountType;
import com.eemeli.orderservice.model.product.BeerProduct;
import com.eemeli.orderservice.model.product.BreadProduct;
import com.eemeli.orderservice.model.product.Product;
import com.eemeli.orderservice.model.product.VegetableProduct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Map;

import static com.eemeli.orderservice.utility.CurrencyConverter.centsToEur;
import static com.eemeli.orderservice.utility.DiscountResolver.*;
import static com.eemeli.orderservice.utility.ProductMapper.mapProductDTOToProduct;

public record ReceiptItemDTO(
        @Positive
        int quantity,
        @NotBlank
        String productName,
        @Positive
        BigDecimal totalPrice
) {
    public static class Builder {
        private final OrderItemDTO orderItemDTO;
        private final Product product;
        private long finalPrice;

        public Builder(OrderItemDTO orderItemDTO) {
            this.orderItemDTO = orderItemDTO;
            this.product = mapProductDTOToProduct(orderItemDTO.product());
            this.finalPrice = this.product.originalPriceInCents(orderItemDTO.quantity());
        }

        public Builder applyDiscounts(Map<DiscountType, Discount> discountsByType) {
            var productQuantity = orderItemDTO.quantity();

            this.finalPrice = switch (product) {
                case BeerProduct beer -> resolveDiscountedBeerPrice(
                        productQuantity,
                        beer,
                        discountsByType.get(DiscountType.BEER_SIX_PACK)
                );
                case BreadProduct bread -> resolveDiscountedBreadPrice(
                        productQuantity,
                        bread.unitPriceInCents(),
                        bread,
                        discountsByType
                );
                case VegetableProduct vegetable when vegetable.weightInGrams() > 500
                        -> resolveDiscountedVegetablesPrice(
                                vegetable.weightInGrams(),
                                vegetable.unitPriceInCents(),
                                discountsByType.get(DiscountType.VEG_PERCENTAGE_500_G)
                );
                case VegetableProduct vegetable when vegetable.weightInGrams() > 100
                        -> resolveDiscountedVegetablesPrice(
                                vegetable.weightInGrams(),
                                vegetable.unitPriceInCents(),
                                discountsByType.get(DiscountType.VEG_PERCENTAGE_100_G)
                );
                case VegetableProduct vegetable
                        -> resolveDiscountedVegetablesPrice(
                                vegetable.weightInGrams(),
                                vegetable.unitPriceInCents(),
                                discountsByType.get(DiscountType.VEG_PERCENTAGE_1_G)
                );
            };

            return this;
        }

        public ReceiptItemDTO build() {
            return new ReceiptItemDTO(
                    orderItemDTO.quantity(),
                    orderItemDTO.product().name(),
                    centsToEur(finalPrice)
            );
        }
    }
}
