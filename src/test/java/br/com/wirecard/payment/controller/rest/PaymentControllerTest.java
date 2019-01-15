package java.br.com.wirecard.payment.controller.rest;

import br.com.wirecard.payment.controller.dto.BoletoDTO;
import br.com.wirecard.payment.controller.dto.BuyerDTO;
import br.com.wirecard.payment.controller.dto.CreditCardDTO;
import br.com.wirecard.payment.controller.dto.PaymentDTO;
import br.com.wirecard.payment.controller.endpoint.PaymentEndPoint;
import br.com.wirecard.payment.controller.rest.PaymentController;
import br.com.wirecard.payment.domain.PaymentStatus;
import br.com.wirecard.payment.domain.PaymentType;
import br.com.wirecard.payment.service.PaymentService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private PaymentService service;

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
    public void createCreditCardPayment() throws Exception{

        mvc.perform(post(PaymentEndPoint.PATH_PAYMENT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].status", Matchers.equalToIgnoringCase(PaymentStatus.APPROVED.getDescription())));
    }
}
