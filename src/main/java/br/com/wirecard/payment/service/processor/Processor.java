package br.com.wirecard.payment.service.processor;

import br.com.wirecard.payment.domain.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class Processor {

    private Map<PaymentType, PaymentProcessor> processors;

    @Autowired
    public Processor(List<PaymentProcessor> allProcessors){
        this.processors = allProcessors
                .stream()
                .collect(Collectors.toMap(PaymentProcessor::getType, p -> p));
    }

    public PaymentProcessor getPaymentProcessor(PaymentType paymentType){
        return processors.get(paymentType);
    }
}
