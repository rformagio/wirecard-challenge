package br.com.wirecard.payment.service.processor;

import br.com.wirecard.payment.domain.CreditCard;
import br.com.wirecard.payment.domain.Payment;
import br.com.wirecard.payment.domain.PaymentStatus;
import br.com.wirecard.payment.domain.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class CreditCardProcessor implements PaymentProcessor {

    PaymentType type;

    public CreditCardProcessor(){
        this.type = PaymentType.CREDIT_CARD;
    }

    @Override
    public boolean process(Payment payment) {

        ///Process credit card payment: business validate, ...
        CreditCard creditCard = (CreditCard)payment;
        creditCard.setIssuer(getCreditCardIssuer(creditCard.getCardNumber()));
        //Mock status APPROVED
        creditCard.setStatus(PaymentStatus.APPROVED);

        return true;
    }

    @Override
    public PaymentType getType() {
        return this.type;
    }

    private String getCreditCardIssuer(String creditCardNumber){
        br.com.moip.validators.CreditCard creditCard = new br.com.moip.validators.CreditCard(creditCardNumber);
        return creditCard.getBrand().name();
    }
}
