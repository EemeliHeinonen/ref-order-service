package com.eemeli.orderservice.dto;

import com.eemeli.orderservice.model.discount.Discount;
import com.eemeli.orderservice.model.discount.DiscountType;
import com.eemeli.orderservice.model.product.BeerProduct;
import com.eemeli.orderservice.model.product.BreadProduct;
import com.eemeli.orderservice.model.product.Product;
import com.eemeli.orderservice.model.product.VegetableProduct;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static com.eemeli.orderservice.utility.CurrencyConverter.centsToEur;
import static com.eemeli.orderservice.utility.DiscountResolver.*;
import static com.eemeli.orderservice.utility.NumberToWordConverter.convertNumberToWord;
import static com.eemeli.orderservice.utility.ProductMapper.mapProductDTOToProduct;

public record ReceiptItemDTO(
        @Positive
        int quantity,
        @NotNull
        Product product,
        @Positive
        BigDecimal totalPrice
) {

    @Override
    public String toString() {
        String formattedPrice = String.format("€%.2f", totalPrice);

        var productName = switch (product) {
            case BreadProduct bread -> {
                var breadAgeDays = ChronoUnit.DAYS.between(bread.createdAtDate(), LocalDate.now());
                String breadAgeText = convertNumberToWord((int) breadAgeDays);
                yield breadAgeDays > 2
                        ? String.format("%s (%s days old)", product.name(), breadAgeText)
                        : product.name();
            }
            default -> product.name();
        };

        return switch (product) {
            case VegetableProduct vegetable -> String.format("%dg %s %s", vegetable.weightInGrams(), product.name(), formattedPrice);
            default -> String.format("%dx %s %s", quantity, productName, formattedPrice);
        };
    }

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
                    product,
                    centsToEur(finalPrice)
            );
        }
    }
}
