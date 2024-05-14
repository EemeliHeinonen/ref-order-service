package com.eemeli.orderservice.controller;

import com.eemeli.orderservice.dto.OrderDTO;
import com.eemeli.orderservice.dto.ReceiptDTO;
import com.eemeli.orderservice.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public @NotNull ResponseEntity<ReceiptDTO> postOrder(@Valid @RequestBody OrderDTO orderDTO) {
        var createdReceipt = orderService.createOrder(orderDTO);
        return ResponseEntity.ok()
                .body(createdReceipt);
    }
}
