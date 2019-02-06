package br.com.wirecard.payment.domain;

import br.com.wirecard.payment.controller.util.CreditCardNumber;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode
@Entity
@PrimaryKeyJoinColumn(name = "creditCardId", referencedColumnName = "paymentId")
public class CreditCard extends Payment {

    public CreditCard(){
        super(PaymentType.CREDIT_CARD);
    }

    @NotEmpty(message = "Credit card holder name is mandatory!")
    private String holderName;

    @NotEmpty(message = "Card number is mandatory!")
    @CreditCardNumber(message = "Credit card number is invalid!")
    private String cardNumber;

    @NotNull(message = "Card expiration date is mandatory!")
    @Future(message = "Card expiration date is invalid !")
    private LocalDate expirationDate;

    @NotEmpty(message = "CVV is mandatory!")
    private String cvv;

    private String issuer;
}
