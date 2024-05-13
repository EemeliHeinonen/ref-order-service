package com.eemeli.orderservice.controller;

import com.eemeli.orderservice.service.DiscountService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/deals", produces = MediaType.APPLICATION_JSON_VALUE)
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public @NotNull ResponseEntity<List<String>> getDiscounts() {
        return ResponseEntity.ok()
                .body(discountService.getAllDiscountDescriptions());
    }
}
