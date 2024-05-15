package com.eemeli.orderservice.integration;

import com.eemeli.orderservice.dto.*;
import com.eemeli.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static com.eemeli.orderservice.TestHelpers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    ProductDTO freshBreadProductDTO = buildBreadProductDTOWithAgeInDays(0);
    ProductDTO threeDayOldBreadProductDTO = buildBreadProductDTOWithAgeInDays(3);
    ProductDTO sixDayOldBreadProductDTO = buildBreadProductDTOWithAgeInDays(6);

    ProductDTO dutchBeerProductDTO = buildBeerProductDTOWithName("Dutch Beer");
    ProductDTO belgianBeerProductDTO = buildBeerProductDTOWithName("Belgian Beer");

    // Bread

    @Test
    void whenOrderingBreadOlderThan6Days_ThenValidationFails() throws Exception {
        // Arrange
        var tooOldBread = buildBreadProductDTOWithAgeInDays(11);

        OrderDTO orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(tooOldBread, 3)
        ));

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {"code":4000101,"description":"Validation error, check the submitted fields","reasons":["orderItems[0].product.createdAtDate: The date must not be older than 6 days"]}
                        """)
                );
    }

    @Test
    void whenOrderingBreadThatWasBakedToday_ThenNoDiscountIsApplied() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(freshBreadProductDTO, 3)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("3x Bread €3,00"),
                BigDecimal.valueOf(3)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrdering3Breads3DayOld_ThenBuy1Take2DiscountIsApplied() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(threeDayOldBreadProductDTO, 3)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("3x Bread (three days old) €2,00"),
                BigDecimal.valueOf(2)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrdering3Breads6DayOld_ThenBuy1Take3DiscountIsApplied() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(sixDayOldBreadProductDTO, 3)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("3x Bread (six days old) €1,00"),
                BigDecimal.valueOf(1)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrdering2Breads6DayOld_ThenBuy1Take3DiscountIsApplied() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(sixDayOldBreadProductDTO, 2)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("2x Bread (six days old) €1,00"),
                BigDecimal.valueOf(1)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrderingBreadWithNoCreatedAtDate_thenValidationFails() throws Exception {
        // Arrange
        var invalidBread = new ProductDTO(
                "Bread",
                ProductDTOCategory.BREAD,
                100,
                null,
                null
        );

        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(invalidBread, 1)
        ));

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                       {"code":4000101,"description":"Validation error, check the submitted fields","reasons":["orderItems[0].product.createdAtDate: BREAD products must have a non-null createdAtDate","orderItems[0].product: Invalid product data"]} 
                        """)
                );
    }

    // Beers

    @Test
    void whenOrdering5Beers_thenNoDiscountIsApplied() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(dutchBeerProductDTO, 5)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("5x Dutch Beer €2,50"),
                BigDecimal.valueOf(2.5)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrdering6OfTheSameBeers_thenSixPackBulkDiscountIsApplied() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(dutchBeerProductDTO, 6)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("6x Dutch Beer €2,00"),
                BigDecimal.valueOf(2)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrdering3DutchAnd3BelgianBeers_thenNoDiscountIsApplied() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(dutchBeerProductDTO, 3),
                new OrderItemDTO(belgianBeerProductDTO, 3)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of(
                        "3x Dutch Beer €1,50",
                        "3x Belgian Beer €1,50"
                ),
                BigDecimal.valueOf(3)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrdering7OfTheSameBeers_thenSixPackBulkDiscountIsApplied() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(dutchBeerProductDTO, 7)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("7x Dutch Beer €2,50"),
                BigDecimal.valueOf(2.5)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrdering12OfTheSameBeers_thenSixPackBulkDiscountIsAppliedTwice() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(dutchBeerProductDTO, 12)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("12x Dutch Beer €4,00"),
                BigDecimal.valueOf(4)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrdering2SixPacksOfDifferentBeers_thenSixPackBulkDiscountIsAppliedForBoth() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(dutchBeerProductDTO, 6),
                new OrderItemDTO(belgianBeerProductDTO, 6)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of(
                        "6x Dutch Beer €2,00",
                        "6x Belgian Beer €2,00"
                ),
                BigDecimal.valueOf(4)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    // Vegetables

    @Test
    void whenOrderingLessThan100gOfVegetables_then5PercentDiscountIsApplied() throws Exception {
        // Arrange
        ProductDTO vegetableProductDTO = buildVegetableProductDTOWithWeight(50);
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(vegetableProductDTO, 1)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("50g Vegetables €0,47"),
                BigDecimal.valueOf(0.47)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrderingBetween100gAnd500gOfVegetables_then7PercentDiscountIsApplied() throws Exception {
        // Arrange
        ProductDTO vegetableProductDTO = buildVegetableProductDTOWithWeight(200);
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(vegetableProductDTO, 1)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("200g Vegetables €1,86"),
                BigDecimal.valueOf(1.86)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrderingMoreThan500gOfVegetables_then10PercentDiscountIsApplied() throws Exception {
        // Arrange
        ProductDTO vegetableProductDTO = buildVegetableProductDTOWithWeight(1000);
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(vegetableProductDTO, 1)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of("1000g Vegetables €9,00"),
                BigDecimal.valueOf(9)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrderingDifferentTypesOfProducts_thenEachDiscountIsApplied() throws Exception {
        // Arrange
        ProductDTO vegetableProductDTO = buildVegetableProductDTOWithWeight(200);
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(threeDayOldBreadProductDTO, 3),
                new OrderItemDTO(vegetableProductDTO, 1),
                new OrderItemDTO(dutchBeerProductDTO, 6)
        ));

        var expectedReceiptDTO = new ReceiptDTO(
                List.of(
                        "3x Bread (three days old) €2,00",
                        "200g Vegetables €1,86",
                        "6x Dutch Beer €2,00"
                ),
                BigDecimal.valueOf(5.86)
        );

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedReceiptDTO))
                );
    }

    @Test
    void whenOrderingVegetablesWithQuantityMoreThan1_thenValidationFails() throws Exception {
        // Arrange
        ProductDTO vegetableProductDTO = buildVegetableProductDTOWithWeight(1000);
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(vegetableProductDTO, 2)
        ));

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {"code":4000101,"description":"Validation error, check the submitted fields","reasons":["orderItems[0].quantity: OrderItemDTOs with a VEGETABLE product must have a quantity of 1","orderItems[0]: Invalid order item data"]}
                        """)
                );
    }

    @Test
    void whenOrderingVegetablesWithNoWeight_thenValidationFails() throws Exception {
        // Arrange
        var invalidVeggies = new ProductDTO(
                "Vegetables",
                ProductDTOCategory.VEGETABLE,
                100,
                null,
                null
        );

        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(invalidVeggies, 1)
        ));

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                       {"code":4000101,"description":"Validation error, check the submitted fields","reasons":["orderItems[0].product: Invalid product data","orderItems[0].product.weightInGrams: VEGETABLE products must have a non-null weightInGrams"]} 
                        """)
                );
    }

    @Test
    void whenOrderingAnItemWithQuantity0_thenValidationFails() throws Exception {
        // Arrange
        var orderDTO = new OrderDTO(List.of(
                new OrderItemDTO(dutchBeerProductDTO, 0)
        ));

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                       {"code":4000101,"description":"Validation error, check the submitted fields","reasons":["orderItems[0].quantity: must be greater than 0"]} 
                        """)
                );
    }
}