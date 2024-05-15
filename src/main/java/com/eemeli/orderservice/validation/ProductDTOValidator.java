package com.eemeli.orderservice.validation;

import com.eemeli.orderservice.dto.ProductDTO;
import com.eemeli.orderservice.dto.ProductDTOCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ProductDTOValidator implements ConstraintValidator<ValidProductDTO, ProductDTO> {

    @Override
    public boolean isValid(ProductDTO productDTO, ConstraintValidatorContext context) {
        if (productDTO == null) {
            return false;
        }

        boolean isValid = true;

        if (productDTO.category() == ProductDTOCategory.BREAD && productDTO.createdAtDate() == null) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("BREAD products must have a non-null createdAtDate")
                    .addPropertyNode("createdAtDate")
                    .addConstraintViolation();
        }

        if (productDTO.category() == ProductDTOCategory.VEGETABLE && productDTO.weightInGrams() == null) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("VEGETABLE products must have a non-null weightInGrams")
                    .addPropertyNode("weightInGrams")
                    .addConstraintViolation();
        }

        return isValid;
    }

//    @Override
//    public void initialize(ValidProductDTO constraintAnnotation) {
//        // No initialization needed
//    }
}
