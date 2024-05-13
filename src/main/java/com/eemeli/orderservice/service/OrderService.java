package com.eemeli.orderservice.service;

import com.eemeli.orderservice.dto.OrderDTO;
import com.eemeli.orderservice.dto.ReceiptDTO;
import com.eemeli.orderservice.dto.ReceiptItemDTO;
import com.eemeli.orderservice.repository.DiscountRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {
    private final DiscountRepository discountRepository;

    public OrderService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public @NotNull ReceiptDTO createOrder(@NotNull OrderDTO orderDTO) {
        var discountsByDiscountType = discountRepository.getDiscountMap();
        var receiptItems = orderDTO.orderItems().stream().map(orderItem ->
                new ReceiptItemDTO.Builder(orderItem)
                        .applyDiscounts(discountsByDiscountType)
                        .build()
        ).toList();

        var receiptTotal = receiptItems.stream()
                .map(ReceiptItemDTO::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ReceiptDTO(receiptItems, receiptTotal);
    }
}
