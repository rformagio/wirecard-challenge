package br.com.wirecard.payment.controller.rest;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(PaymentEndPoint.PATH_SERVICES)
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    ValidateInputData validateInputData;


    @ApiOperation(value = "Verify status of a payment. Returns a Payment object.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "The payment id is not valid."),
            @ApiResponse(code = 404, message = "Payment not found for payment Id informed.")
    }
    )
    @GetMapping(PaymentEndPoint.PATH_STATUS)
    @ResponseStatus(HttpStatus.FOUND)
    public Payment verifyStatusByPaymentId(@PathVariable String paymentId){

        Long id = validateInputData.validadePaymentId(paymentId);
        Payment payment = paymentService.verifyStatusByPaymentId(id);

        if(payment==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    Messages.PAYMENT_NOT_FOUND);
        }

        return payment;
    }

    @ApiOperation(value = "Creates a new payment")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully creates a payment"),
            @ApiResponse(code = 400, message = "Some input data can be wrong")
    }
    )
    @PostMapping(PaymentEndPoint.PATH_PAYMENT)
    @ResponseStatus(HttpStatus.CREATED)
    public Payment createPayment(@RequestBody Payment payment){

        validateInputData.validatePayment(payment);

        return paymentService.createPayment(payment);
    }

}
