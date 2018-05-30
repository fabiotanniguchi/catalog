package br.unicamp.sindo.catalog.external.payment.rest;

import br.unicamp.sindo.catalog.external.payment.dto.WebsitePaymentData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/external/payment")
public class PaymentsRest {

    private static final String PAYMENTS_HOST = "https://payment-server-mc851.herokuapp.com/";

    private static final String PAYMENTS_OUR_SITEID = "c12cce3b9938ca5e410d7347e28d10d0df7869ad";

    private static final String EVALUATE_CREDIT_CARD_PAYMENT_PATH = "payments/creditCard";
    private static final String SUBMIT_BOLETO_PAYMENT_PATH = "payments/bankTicket";
    private static final String BOLETO_STATUS_PATH = "bankTicket/{code}/status";
    private static final String INVOICE_PATH = "invoice";
    private static final String CREDIT_CARD_PATH = "creditCard";
    private static final String CREDIT_CARD_QUERY_PATH = "creditCard/{number}";

    @PostMapping
    public ResponseEntity<String> insertCustomer1(@RequestBody WebsitePaymentData paymentData) {

        return null;
    }
}
