package br.com.wirecard.payment.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long buyerId;
    private String name;
    private String email;
    private String cpf;
}

