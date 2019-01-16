package br.com.wirecard.payment.controller.rest;

import br.com.moip.creditcard.Brands;
import br.com.wirecard.payment.controller.endpoint.PaymentEndPoint;
import br.com.wirecard.payment.controller.util.ValidateInputData;
import br.com.wirecard.payment.domain.*;
import br.com.wirecard.payment.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static br.com.wirecard.payment.TestUtil.*;
import static org.mockito.BDDMockito.given;
import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private PaymentService service;

    @MockBean
    private ValidateInputData validateInputData;

    private static final String CREDIT_CARD_AMEX = "378282246310005";
    private static final String BAR_CODE = "0000000090 0000007800 0000560000 0034000000";
    private static final String CPF = "";
    private static final String NAME = "Antonio Carlos";
    private static final String EMAIL = "antonio@gmail.com";

    @TestConfiguration
    static class CustomizationConfiguration implements RestDocsMockMvcConfigurationCustomizer {

        @Override
        public void customize(MockMvcRestDocumentationConfigurer configurer) {
            configurer.operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint());
        }

        @Bean
        public RestDocumentationResultHandler restDocumentation() {
            return MockMvcRestDocumentation.document("{method-name}");
        }
    }

    @Test
    public void givenTheCCardPayment_whenCreate_thenReturnThePaymentWithStatusAndIssuerAndPaymentId() throws Exception{

        Long paymentId = 101L;
        Long clientId = 2L;

        Buyer buyer1 = getBuyer(NAME, CPF, EMAIL);

        CreditCard creditCardToCreate = new CreditCard();
        creditCardToCreate.setCvv("4");
        creditCardToCreate.setCardNumber(CREDIT_CARD_AMEX);
        creditCardToCreate.setHolderName(NAME);
        creditCardToCreate.setExpirationDate(parseToLocalDate("2019-01-15"));
        creditCardToCreate.setBuyer(buyer1);
        creditCardToCreate.setAmount(BigDecimal.valueOf(200.00));
        creditCardToCreate.setClientId(clientId);

        Buyer buyer2 = getBuyer(NAME, CPF, EMAIL);
        buyer2.setBuyerId(1L);

        CreditCard creditCardCreated = new CreditCard();
        creditCardCreated.setCvv("4");
        creditCardCreated.setCardNumber(CREDIT_CARD_AMEX);
        creditCardCreated.setHolderName(NAME);
        creditCardCreated.setExpirationDate(parseToLocalDate("2019-01-15"));
        creditCardCreated.setBuyer(buyer2);
        creditCardCreated.setAmount(BigDecimal.valueOf(200.00));
        creditCardCreated.setClientId(clientId);
        creditCardCreated.setStatus(PaymentStatus.APPROVED);
        creditCardCreated.setPaymentId(paymentId);
        creditCardCreated.setIssuer(Brands.AMERICAN_EXPRESS.name());

        given (service.createPayment(creditCardToCreate)).willReturn(creditCardCreated);

        mvc.perform(post(PaymentEndPoint.PATH_SERVICES + PaymentEndPoint.PATH_PAYMENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(creditCardToCreate)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.status", is(PaymentStatus.APPROVED.name())))
                .andExpect(jsonPath("$.type", is(PaymentType.CREDIT_CARD.name())))
                .andExpect(jsonPath("$.issuer",is(Brands.AMERICAN_EXPRESS.name())))
                .andExpect(jsonPath("$.paymentId", is(paymentId.intValue())));
    }

    @Test
    public void givenTheBoletoPayment_whenCreate_thenReturnThePaymentWithStatusAndBrCodeAndPaymentId() throws Exception{

        Long paymentId = 101L;
        Long clientId = 2L;

        Buyer buyer1 = getBuyer(NAME, CPF, EMAIL);

        Boleto boletoToCreate = new Boleto();
        boletoToCreate.setBuyer(buyer1);
        boletoToCreate.setAmount(BigDecimal.valueOf(200.00));
        boletoToCreate.setClientId(clientId);

        Buyer buyer2 = getBuyer(NAME, CPF, EMAIL);
        buyer2.setBuyerId(1L);

        Boleto boletoCreated = new Boleto();
        boletoCreated.setBuyer(buyer2);
        boletoCreated.setAmount(BigDecimal.valueOf(200.00));
        boletoCreated.setBarCode(BAR_CODE);
        boletoCreated.setClientId(clientId);
        boletoCreated.setStatus(PaymentStatus.APPROVED);
        boletoCreated.setPaymentId(paymentId);

        given (service.createPayment(boletoToCreate)).willReturn(boletoCreated);

        mvc.perform(post(PaymentEndPoint.PATH_SERVICES + PaymentEndPoint.PATH_PAYMENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(boletoToCreate)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.status", is(PaymentStatus.APPROVED.name())))
                .andExpect(jsonPath("$.type", is(PaymentType.BOLETO.name())))
                .andExpect(jsonPath("$.barCode",is(BAR_CODE)))
                .andExpect(jsonPath("$.paymentId", is(paymentId.intValue())));
    }

    @Test
    public void givenPaymentId_whenVerifiedStatus_thenReturnPayment() throws Exception{

        Long paymentId = 101L;

        given(validateInputData.validadePaymentId(paymentId.toString())).willReturn(paymentId);
        given (service.verifyStatusByPaymentId(paymentId)).willReturn(null);

        mvc.perform(get(PaymentEndPoint.PATH_SERVICES + PaymentEndPoint.PATH_PAYMENT + "/" + paymentId + "/status")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void givenPaymentId_whenVerifiedStatus_thenReturnNull() throws Exception{

        Long paymentId = 101L;
        Long clientId = 2L;

        Buyer buyer = getBuyer(NAME, CPF, EMAIL);

        Boleto boletoToBeVerified = new Boleto();
        boletoToBeVerified.setBuyer(buyer);
        boletoToBeVerified.setAmount(BigDecimal.valueOf(200.00));
        boletoToBeVerified.setClientId(clientId);
        boletoToBeVerified.setPaymentId(paymentId);
        boletoToBeVerified.setBarCode(BAR_CODE);
        boletoToBeVerified.setStatus(PaymentStatus.APPROVED);

        given(validateInputData.validadePaymentId(paymentId.toString())).willReturn(paymentId);
        given (service.verifyStatusByPaymentId(paymentId)).willReturn(boletoToBeVerified);

        mvc.perform(get(PaymentEndPoint.PATH_SERVICES + PaymentEndPoint.PATH_PAYMENT + "/" + paymentId + "/status")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andDo(print())
                .andExpect(jsonPath("$.status", is(PaymentStatus.APPROVED.name())))
                .andExpect(jsonPath("$.type", is(PaymentType.BOLETO.name())))
                .andExpect(jsonPath("$.barCode",is(BAR_CODE)))
                .andExpect(jsonPath("$.paymentId", is(paymentId.intValue())));


    }


    private Buyer getBuyer(String name, String cpf, String email){

        Buyer buyer = new Buyer();
        buyer.setCpf(cpf);
        buyer.setName(name);
        buyer.setEmail(email);
        return buyer;
    }
}
