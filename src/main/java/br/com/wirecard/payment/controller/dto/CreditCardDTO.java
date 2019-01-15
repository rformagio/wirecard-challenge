package br.com.wirecard.payment.controller.dto;

import br.com.wirecard.payment.controller.util.CreditCardNumber;
import br.com.wirecard.payment.domain.PaymentType;
import lombok.Data;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
public class CreditCardDTO extends PaymentDTO {

    public CreditCardDTO(){
        super(PaymentType.CREDIT_CARD);
    }

    @NotEmpty(message = "Credit card holder name is mandatory!")
    private String holderName;
    @NotEmpty(message = "Card number is mandatory!")
    @CreditCardNumber(message = "Credit card number is invalid!")
    private String cardNumber;
    @NotEmpty(message = "CVV is mandatory!")
    private String cvv;
    @NotNull(message = "Card expiration date is mandatory!")
    @Future(message = "Card expiration date is invalid !")
    private LocalDate expirationDate;

    private String issuer;
}
