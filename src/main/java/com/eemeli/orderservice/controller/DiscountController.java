package com.eemeli.orderservice.controller;

import com.eemeli.orderservice.controller.resources.DiscountResources;
import com.eemeli.orderservice.service.DiscountService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/discounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class DiscountController implements DiscountResources {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public @NotNull ResponseEntity<List<String>> getDiscounts() {
        var discountDescriptions = discountService
                .getAllDiscountDescriptions()
                .stream()
                .sorted()
                .toList();

        return ResponseEntity.ok()
                .body(discountDescriptions);
    }
}
