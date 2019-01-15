package br.com.wirecard.payment.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
public class BuyerDTO {

    Long buyerId;
    @NotEmpty(message = "Buyer name is mandatory!")
    String name;
    @Email(message = "E-mail valid is mandatory!")
    String email;
    @NotEmpty(message = "Cpf is mandatory!")
    String cpf;
}
