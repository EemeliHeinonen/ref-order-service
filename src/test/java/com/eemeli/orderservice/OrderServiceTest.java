package com.eemeli.orderservice;

import com.eemeli.orderservice.dto.*;
import com.eemeli.orderservice.model.discount.Discount;
import com.eemeli.orderservice.model.discount.DiscountType;
import com.eemeli.orderservice.repository.DiscountRepository;
import com.eemeli.orderservice.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    private OrderService orderService;
    private DiscountRepository discountRepository;
    private List<OrderItemDTO> orderItems;

    @BeforeEach
    void setUp() {
        discountRepository = Mockito.mock(DiscountRepository.class);
        orderService = new OrderService(discountRepository);
        orderItems = List.of(
                new OrderItemDTO(
                        new ProductDTO(
                                "Bread",
                                ProductDTOCategory.BREAD,
                                100,
                                null,
                                LocalDate.now().minusDays(3)
                        ),
                        3
                )
        );
    }

    @Test
    void creatingAnOrderWithBreadWhenThereAreNoDiscountsReturnsReceiptWithNoDiscounts() {
        // Arrange
        Map<DiscountType, Discount> discounts = new HashMap<>();
        when(discountRepository.getDiscountMap()).thenReturn(discounts);

        var orderDTO = new OrderDTO(orderItems);

        // Act
        ReceiptDTO receipt = orderService.createOrder(orderDTO);
        var expectedReceiptTotal = BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_UP);

        // Assert
        assertEquals(expectedReceiptTotal, receipt.receiptTotal());
    }
}