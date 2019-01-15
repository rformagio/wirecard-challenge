package br.com.wirecard.payment.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "boletoId", referencedColumnName = "paymentId")
public class Boleto extends Payment {

    public Boleto(){
        super(PaymentType.BOLETO);
    }

    private String barCode;

}
