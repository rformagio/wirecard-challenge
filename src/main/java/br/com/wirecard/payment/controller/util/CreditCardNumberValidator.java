package br.com.wirecard.payment.controller.util;

import br.com.moip.validators.CreditCard;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreditCardNumberValidator implements ConstraintValidator<CreditCardNumber, String> {

    private CreditCardNumber annotation;

    @Override
    public void initialize( CreditCardNumber constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (null == value) {
            return false;
        }

        CreditCard validator = new CreditCard(value);
        if (validator.isValid()) {
                return true;
        }

        return false;
    }
}
