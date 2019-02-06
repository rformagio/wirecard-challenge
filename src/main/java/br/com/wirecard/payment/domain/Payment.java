package br.com.wirecard.payment.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Boleto.class, name = "BOLETO"),
        @JsonSubTypes.Type(value = CreditCard.class, name = "CREDIT_CARD")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    @NotNull(message = "Client Id is mandatory!")
    private Long clientId;

    @NotNull(message = "Amount is mandatory!")
    private BigDecimal amount;

    @NotNull
    private final PaymentType type;

    private PaymentStatus status;

    @Valid
    @ManyToOne
    @JoinColumn(name = "buyerId")
    private Buyer buyer;
}
