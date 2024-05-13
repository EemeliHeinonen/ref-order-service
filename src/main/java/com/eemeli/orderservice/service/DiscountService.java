package com.eemeli.orderservice.service;

import com.eemeli.orderservice.model.discount.Discount;
import com.eemeli.orderservice.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.getAllDiscounts();
    }

    public List<String> getAllDiscountDescriptions() {
        return getAllDiscounts().stream().map(Discount::description).toList();
    }
}
