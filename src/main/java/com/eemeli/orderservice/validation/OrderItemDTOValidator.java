package com.eemeli.orderservice.validation;

import com.eemeli.orderservice.dto.OrderItemDTO;
import com.eemeli.orderservice.dto.ProductDTOCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OrderItemDTOValidator implements ConstraintValidator<ValidOrderItemDTO, OrderItemDTO> {

    @Override
    public boolean isValid(OrderItemDTO orderItemDTO, ConstraintValidatorContext context) {
        if (orderItemDTO == null) {
            return false;
        }

        boolean isValid = true;

        if (orderItemDTO.product().category() == ProductDTOCategory.VEGETABLE && orderItemDTO.quantity() != 1) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("OrderItemDTOs with a VEGETABLE product must have a quantity of 1")
                    .addPropertyNode("quantity")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
