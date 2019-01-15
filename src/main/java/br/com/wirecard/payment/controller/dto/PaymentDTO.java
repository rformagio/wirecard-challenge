package br.com.wirecard.payment.controller.dto;

import br.com.wirecard.payment.domain.PaymentStatus;
import br.com.wirecard.payment.domain.PaymentType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BoletoDTO.class, name = "BOLETO"),
        @JsonSubTypes.Type(value = CreditCardDTO.class, name = "CREDIT_CARD")
})
public abstract class PaymentDTO implements Serializable {

    private Long paymentId;
    @NotNull(message = "Client Id is mandatory!")
    private Long clientId;
    @NotNull(message = "Amount is mandatory!")
    private BigDecimal amount;
    private final PaymentType type;
    private PaymentStatus status;
    @Valid
    private BuyerDTO buyer;

}
