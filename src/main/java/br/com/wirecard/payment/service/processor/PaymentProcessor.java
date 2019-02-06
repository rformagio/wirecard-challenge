package br.com.wirecard.payment.service.processor;

import br.com.wirecard.payment.domain.Payment;
import br.com.wirecard.payment.domain.PaymentType;

public interface PaymentProcessor {

    boolean process(Payment payment);

    PaymentType getType();

}
