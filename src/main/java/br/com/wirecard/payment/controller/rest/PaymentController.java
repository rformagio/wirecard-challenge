package br.com.wirecard.payment.controller.rest;

import br.com.wirecard.payment.controller.dto.BoletoDTO;
import br.com.wirecard.payment.controller.dto.BuyerDTO;
import br.com.wirecard.payment.controller.dto.CreditCardDTO;
import br.com.wirecard.payment.controller.dto.PaymentDTO;
import br.com.wirecard.payment.controller.util.Messages;
import br.com.wirecard.payment.controller.util.ValidateInputData;
import br.com.wirecard.payment.domain.*;
import br.com.wirecard.payment.controller.endpoint.PaymentEndPoint;
import br.com.wirecard.payment.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(PaymentEndPoint.PATH_SERVICES)
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ValidateInputData validateInputData;


    @ApiOperation(value = "Verify status of a payment. Returns a Payment object.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "The payment id is not valid."),
            @ApiResponse(code = 404, message = "Payment not found for payment Id informed.")
    }
    )
    @GetMapping(PaymentEndPoint.PATH_STATUS)
    public ResponseEntity<PaymentDTO> verifyStatusByPaymentId(@PathVariable String paymentId){

        PaymentDTO dto;
        Long id = validateInputData.validadePaymentId(paymentId);

        Payment payment = paymentService.verifyStatusByPaymentId(id);

        if(payment==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    Messages.PAYMENT_NOT_FOUND);
        }

        try {
            dto = convertEntityToDto(payment);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERRO !!!!!!!!",
                    e);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @ApiOperation(value = "Creates a new payment")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully creates a payment"),
            @ApiResponse(code = 400, message = "Some input data can be wrong")
    }
    )
    @PostMapping(PaymentEndPoint.PATH_PAYMENT)
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO){

        validateInputData.validatePaymentDTO(paymentDTO);
        Payment payment = convertDtoToEntity(paymentDTO);
        PaymentDTO dto = convertEntityToDto(paymentService.createPayment(payment));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    private PaymentDTO convertEntityToDto(Payment payment){
        PaymentDTO dto;

        if(payment instanceof Boleto){
            BoletoDTO boletoDTO;
            boletoDTO = modelMapper.map(payment, BoletoDTO.class);
            dto = boletoDTO;
        } else if(payment instanceof CreditCard) {
            CreditCardDTO creditCardDTO;
            creditCardDTO = modelMapper.map(payment, CreditCardDTO.class);
            dto = creditCardDTO;
        } else {
            dto = null;
        }

        return dto;
    }

   private Payment convertDtoToEntity(PaymentDTO dto){
        Payment payment;

        if(dto.getType() == PaymentType.BOLETO){
            payment = modelMapper.map(dto, Boleto.class);
        } else if(dto.getType() == PaymentType.CREDIT_CARD){
            payment = modelMapper.map(dto, CreditCard.class);
        } else {
            return  null;
        }

        return payment;
    }


}
