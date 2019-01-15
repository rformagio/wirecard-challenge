package br.com.wirecard.payment.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "creditCardId", referencedColumnName = "paymentId")
public class CreditCard extends Payment {

    public CreditCard(){
        super(PaymentType.CREDIT_CARD);
    }

    private String holderName;
    private String cardNumber;
    private Date expirationDate;
    private String cvv;
    private String issuer;
}
