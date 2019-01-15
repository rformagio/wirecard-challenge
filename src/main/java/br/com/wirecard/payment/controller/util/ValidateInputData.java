package br.com.wirecard.payment.controller.util;

import br.com.wirecard.payment.controller.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ValidateInputData implements Serializable {

    @Autowired
    Validator validator;

    public Long validadePaymentId(String paymentId){

        if(StringUtils.isEmpty(paymentId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    Messages.MISSING_PAYMENT_ID);
        } else if(!paymentId.matches("[0-9]+")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    Messages.PAYMENT_ID_IS_NOT_A_NUMBER);
        }

        Long ret = null;

        try{
            ret = Long.valueOf(paymentId);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    Messages.UNEXPECTED_ERROR,
                    e);
        }

        return ret;
    }

    public void validatePaymentDTO(PaymentDTO paymentDTO){
        StringBuilder errors = new StringBuilder();
        Set<ConstraintViolation<PaymentDTO>> violations = validator.validate(paymentDTO);
        if(!violations.isEmpty()) {
            for (ConstraintViolation<PaymentDTO> violation : violations) {
                errors.append(violation.getMessage()).append(" | ");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.toString());
        }

    }
}
