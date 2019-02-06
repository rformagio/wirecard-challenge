package br.com.wirecard.payment.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode
@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long buyerId;

    @NotEmpty(message = "Buyer name is mandatory!")
    private String name;

    @Email(message = "E-mail valid is mandatory!")
    private String email;

    @NotEmpty(message = "Cpf is mandatory!")
    private String cpf;
}

