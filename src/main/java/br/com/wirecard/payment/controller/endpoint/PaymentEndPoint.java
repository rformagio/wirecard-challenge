package br.com.wirecard.payment.controller.endpoint;

public interface PaymentEndPoint {


    String PARAM_PAYMENT_ID = "paymentId";

    String PATH_PARAM_PAYMENT_ID = "{" + PARAM_PAYMENT_ID + "}";

    String PATH_SERVICES = "/services";
    String PATH_PAYMENT = "/payments";
    String PATH_STATUS = PATH_PAYMENT + "/" + PATH_PARAM_PAYMENT_ID + "/status";

}
