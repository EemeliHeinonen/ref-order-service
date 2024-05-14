package com.eemeli.orderservice.controller.resources;

import com.eemeli.orderservice.dto.ErrorDTO;
import com.eemeli.orderservice.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DiscountResources {
    @Operation(
            summary = "Gets all discounts",
            tags = {"Discounts"},
            description = "Retrieves a list of all discount descriptions",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = String.class))
                            )
                    ),
                    @ApiResponse(
                            description = "Failure",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<List<String>> getDiscounts();
}
