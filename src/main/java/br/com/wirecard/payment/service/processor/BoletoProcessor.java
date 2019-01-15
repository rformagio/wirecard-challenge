package br.com.wirecard.payment.service.processor;

import br.com.wirecard.payment.domain.Boleto;
import br.com.wirecard.payment.domain.Payment;
import br.com.wirecard.payment.domain.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class BoletoProcessor implements PaymentProcessor {

    private PaymentType type;

    public BoletoProcessor(){
        this.type = PaymentType.BOLETO;
    }

    @Override
    public boolean process(Payment payment) {

        //Process boleto payment: business validate, generate bar code, ...

        ((Boleto)payment).setBarCode(generateBarCode());

        return true;
    }

    @Override
    public PaymentType getType() {
        return this.type;
    }

    private String generateBarCode(){
        //mock
        return "0000000091 0000007800 0000560000 0034000000";
    }
}
