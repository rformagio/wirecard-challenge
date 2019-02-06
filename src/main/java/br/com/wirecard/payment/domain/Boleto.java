package br.com.wirecard.payment.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@Entity
@PrimaryKeyJoinColumn(name = "boletoId", referencedColumnName = "paymentId")
public class Boleto extends Payment {

    public Boleto(){
        super(PaymentType.BOLETO);
    }

    private String barCode;

}
