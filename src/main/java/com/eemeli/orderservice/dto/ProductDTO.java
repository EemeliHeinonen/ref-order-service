package com.eemeli.orderservice.dto;

import com.eemeli.orderservice.validation.NotOlderThanDays;
import com.eemeli.orderservice.validation.ValidProductDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@ValidProductDTO
public record ProductDTO(
        @NotBlank
        String name,
        @NotNull
        ProductDTOCategory category,
        @Positive
        int unitPriceInCents,
        @Positive
        @Nullable
        Integer weightInGrams,
        @PastOrPresent
        @NotOlderThanDays(days = 6, message = "The date must not be older than 6 days")
        @Nullable
        LocalDate createdAtDate
        )  {
}
