package com.eemeli.orderservice.model.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ReceiptDTO(
        @NotNull List<ReceiptItemDTO> receiptItems,
        @NotNull BigDecimal receiptTotal
) {
}
