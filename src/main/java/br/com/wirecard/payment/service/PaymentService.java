package br.com.wirecard.payment.service;

import br.com.wirecard.payment.domain.Buyer;
import br.com.wirecard.payment.domain.Payment;
import br.com.wirecard.payment.persistence.BuyerRepository;
import br.com.wirecard.payment.persistence.PaymentRepository;
import br.com.wirecard.payment.service.processor.PaymentProcessor;
import br.com.wirecard.payment.service.processor.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    Processor processor;

    public Payment createPayment(Payment payment){

        PaymentProcessor paymentProcessor = processor.getPaymentProcessor(payment.getType());
        paymentProcessor.process(payment);

        Buyer buyer = payment.getBuyer();
        buyerRepository.save(buyer);
        payment.setBuyer(buyer);
        Payment result = paymentRepository.save(payment);

        return result;
    }

    public Payment verifyStatusByPaymentId(Long paymentId){
        Optional<Payment> optional = paymentRepository.findById(paymentId);
        if(optional.isPresent())
            return optional.get();
        else
            return null;
    }

}
