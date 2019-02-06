package br.com.wirecard.payment.controller.util;

import br.com.wirecard.payment.domain.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.Set;

@Slf4j
@Component
public class ValidateInputData implements Serializable {

    @Autowired
    Validator validator;

    public Long validadePaymentId(String paymentId){

        if(StringUtils.isEmpty(paymentId)){
            log.warn(Messages.MISSING_PAYMENT_ID);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    Messages.MISSING_PAYMENT_ID);
        } else if(!paymentId.matches("[0-9]+")){
            log.warn(Messages.PAYMENT_ID_IS_NOT_A_NUMBER);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    Messages.PAYMENT_ID_IS_NOT_A_NUMBER);
        }

        Long ret;

        try{
            ret = Long.valueOf(paymentId);
        }catch(Exception e){
            log.error("Error verifying paymentId: " + paymentId, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    Messages.UNEXPECTED_ERROR,
                    e);
        }

        return ret;
    }

    public void validatePayment(Payment payment){
        StringBuilder errors = new StringBuilder();
        Set<ConstraintViolation<Payment>> violations = validator.validate(payment);
        if(!violations.isEmpty()) {
            for (ConstraintViolation<Payment> violation : violations) {
                errors.append(violation.getMessage()).append(" | \n");
            }
            log.warn("Error creating payment: \n " + errors.toString());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.toString());
        }

    }
}
