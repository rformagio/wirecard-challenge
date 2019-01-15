package br.com.wirecard.payment.controller.rest;

import br.com.moip.creditcard.Brands;
import br.com.wirecard.payment.controller.dto.BoletoDTO;
import br.com.wirecard.payment.controller.dto.BuyerDTO;
import br.com.wirecard.payment.controller.dto.CreditCardDTO;
import br.com.wirecard.payment.controller.dto.PaymentDTO;
import br.com.wirecard.payment.controller.endpoint.PaymentEndPoint;
import br.com.wirecard.payment.controller.util.ValidateInputData;
import br.com.wirecard.payment.domain.PaymentStatus;
import br.com.wirecard.payment.domain.PaymentType;
import br.com.wirecard.payment.service.PaymentService;
import org.junit.Before;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private PaymentService service;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ValidateInputData validateInputData;

    private static String CREDIT_CARD_AMEX = "378282246310005";

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

    @Before
    public void init(){

        String cardNumberAMEX = "378282246310005";
        String cardNumberMASTER = "5105105105105100";
        String cardNymberInvalid = "12345678890987";

        PaymentDTO boleto;
        PaymentDTO creditCard;

        BuyerDTO buyer1 = new BuyerDTO();
        buyer1.setName("Antonio Carlos");
        buyer1.setCpf("1234567-9");
        buyer1.setEmail("antonio@gmail.com");

        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setCvv("4");
        creditCardDTO.setHolderName("Antonio Carlos");
        creditCardDTO.setExpirationDate(new Date());
        creditCardDTO.setBuyer(buyer1);
        creditCard = creditCardDTO;
        creditCard.setAmount(new BigDecimal(200.00));

        BoletoDTO boletoDTO = new BoletoDTO();
        boletoDTO.setBuyer(buyer1);
        boleto = boletoDTO;
        boleto.setAmount(new BigDecimal(100.00));

    }

    @Test
    public void givenTheCCardPayment_whenProcessAndSave_thenReturnThePaymentWithStatusAndIssuerAndPaymentId() throws Exception{

        PaymentDTO creditCard;

        BuyerDTO buyer1 = new BuyerDTO();
        buyer1.setName("Antonio Carlos");
        buyer1.setCpf("1234567-9");
        buyer1.setEmail("antonio@gmail.com");

        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setCvv("4");
        creditCardDTO.setCardNumber(CREDIT_CARD_AMEX);
        creditCardDTO.setHolderName("Antonio Carlos");
        creditCardDTO.setExpirationDate(new Date());
        creditCardDTO.setBuyer(buyer1);
        creditCard = creditCardDTO;
        creditCard.setAmount(new BigDecimal(200.00));

        mvc.perform(post(PaymentEndPoint.PATH_PAYMENT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].status", is(PaymentStatus.APPROVED.getDescription())))
                .andExpect(jsonPath("$[0].type", is(PaymentType.CREDIT_CARD.getDescription())))
                .andExpect(jsonPath("$[0].issuer",is(Brands.AMERICAN_EXPRESS.name())))
                .andExpect(jsonPath("$[0].paymentId", is(anyLong())));
    }
}
