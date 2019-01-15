package br.com.wirecard.payment.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    private Long clientId;

    private BigDecimal amount;
    private final PaymentType type;
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "buyerId")
    private Buyer buyer;
}
