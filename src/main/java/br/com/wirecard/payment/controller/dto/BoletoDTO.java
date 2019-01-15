package br.com.wirecard.payment.controller.dto;

import br.com.wirecard.payment.domain.PaymentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class BoletoDTO extends PaymentDTO {

    public BoletoDTO(){
        super(PaymentType.BOLETO);
    }

    private String barCode;
}
