package com.eemeli.orderservice.controller.resources;

import com.eemeli.orderservice.dto.ErrorDTO;
import com.eemeli.orderservice.dto.OrderDTO;
import com.eemeli.orderservice.dto.ReceiptDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


public interface OrderResources {
    @Operation(
            summary = "Creates an order",
            tags = {"Orders"},
            description = "Creates a new order from the order sent in the body and returns the receipt",
            responses = {
                    @ApiResponse(
                            description = "Order created successfully",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReceiptDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid order data",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<ReceiptDTO> postOrder(@Valid @RequestBody OrderDTO orderDTO);
}
