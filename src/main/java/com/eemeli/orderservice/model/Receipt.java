package com.eemeli.orderservice.model;

import com.eemeli.orderservice.model.discount.Discount;
import com.eemeli.orderservice.model.dto.OrderItemDTO;
import com.eemeli.orderservice.model.dto.ReceiptItemDTO;
import com.eemeli.orderservice.model.product.BeerProduct;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record Receipt(@NotNull List<ReceiptItemDTO> receiptItems, long totalPrice) {
    public static class Builder {
        private final List<OrderItemDTO> orderItems;
        private final List<ReceiptItemDTO> receiptItems;

        public Builder(List<OrderItemDTO> orderItems, List<ReceiptItemDTO> receiptItems) {
            this.orderItems = orderItems;
            this.receiptItems = receiptItems;
        }

        public Builder applyDiscounts(List<Discount> discounts) {
            orderItems.forEach(orderItem -> {});
            return this;
        }
    }
}
