package br.com.wirecard.payment.controller.util;

import br.com.moip.creditcard.Brands;

public enum CreditCardIssuer {

    MASTERCARD(Brands.MASTERCARD.name()),
    VISA(Brands.VISA.name()),
    ELO(Brands.ELO.name()),
    DINERS(Brands.DINERS.name()),
    AMERICAN_EXPRESS(Brands.AMERICAN_EXPRESS.name()),
    HIPERCARD(Brands.HIPERCARD.name()),
    HIPER(Brands.HIPER.name()),
    ALL(Brands.MASTERCARD.name() +
            Brands.VISA +
            Brands.ELO +
            Brands.DINERS +
            Brands.AMERICAN_EXPRESS +
            Brands.HIPERCARD +
            Brands.HIPER);

   String name;

   CreditCardIssuer(String name){
       this.name = name;
   }

}
