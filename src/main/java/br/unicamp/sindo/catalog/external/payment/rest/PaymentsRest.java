package br.unicamp.sindo.catalog.external.payment.rest;

import br.unicamp.sindo.catalog.external.logistics.PackageType;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageFromDTO;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageInsertResultDTO;
import br.unicamp.sindo.catalog.external.logistics.rest.LogisticsRest;
import br.unicamp.sindo.catalog.external.payment.dto.*;
import br.unicamp.sindo.catalog.order.OrderService;
import br.unicamp.sindo.catalog.product.Product;
import br.unicamp.sindo.catalog.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping(value = "/external/payment")
public class PaymentsRest {

    private static final String OUR_CEP = "13070712";
    private static final String PAYMENTS_HOST = "https://payment-server-mc851.herokuapp.com/";
    private static final String EVALUATE_CREDIT_CARD_PAYMENT_PATH = "payments/creditCard";
    private static final String SUBMIT_BOLETO_PAYMENT_PATH = "payments/bankTicket";
    private static final String BOLETO_STATUS_PATH = "bankTicket/{code}/status";
    private static final String INVOICE_PATH = "invoice";
    private static final String CREDIT_CARD_PATH = "creditCard";
    private static final String CREDIT_CARD_QUERY_PATH = "creditCard/{number}";
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private LogisticsRest logistics;

    @PostMapping
    public ResponseEntity<String> postPayment(@RequestBody WebsitePaymentData paymentData) {
        final String uri = PAYMENTS_HOST + EVALUATE_CREDIT_CARD_PAYMENT_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        PaymentData data = PaymentData.from(paymentData);

        this.tryToInsertCreditCard(data);

        HttpEntity<PaymentData> entity = new HttpEntity<>(data, headers);

        ResponseEntity<PaymentResultData> response = null;
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.postForEntity(uri, entity, PaymentResultData.class);

        if (response.getBody() != null) {
            if (StringUtils.isEmpty(response.getBody().getErrorMessage())) {
                persistOrder(paymentData, response.getBody());
                return ResponseEntity.ok("");
            } else { // error
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else { // error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void tryToInsertCreditCard(PaymentData paymentData) {
        final String uri = PAYMENTS_HOST + CREDIT_CARD_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreditCardInsertData> entity = new HttpEntity<>(CreditCardInsertData.from(paymentData), headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForEntity(uri, entity, CreditCardData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void persistOrder(WebsitePaymentData data, PaymentResultData payment) {

        LogisticsPackageFromDTO logisticsPackage = new LogisticsPackageFromDTO();
        logisticsPackage.setDeliveryType((byte) data.getDeliveryType().ordinal());
        logisticsPackage.setFromCEP(OUR_CEP);
        logisticsPackage.setToCEP(data.getDeliveryCep());
        logisticsPackage.setPackageType((byte) PackageType.BOX.ordinal());

        double length = 0.0;
        double height = 0.0;
        double width = 0.0;
        double weight = 0.0;
        for (OrderProductData product : data.getProducts()) {
            double productLength = 1.0;
            double productHeight = 1.0;
            double productWidth = 1.0;
            double productWeight = 1.0;

            Product p = productService.fetch(UUID.fromString(product.getProductId()));
            if (p != null) {
                if (p.getHeight() != null) {
                    productHeight = p.getHeight();
                }
                if (p.getLength() != null) {
                    productLength = p.getLength();
                }
                if (p.getWidth() != null) {
                    productWidth = p.getWidth();
                }
                if (p.getWeight() != null) {
                    productWeight = p.getWeight();
                }

                weight += productWeight;
                length += productLength;
                width += productWidth;
                height += productHeight;
            }
        }

        logisticsPackage.setWeight((long) Math.ceil(weight * 100));
        logisticsPackage.setWidth((long) Math.ceil(width * 100));
        logisticsPackage.setLength((long) Math.ceil(length * 100));
        logisticsPackage.setHeight((long) Math.ceil(height * 100));

        String logisticsId = "ERROR";
        ResponseEntity<LogisticsPackageInsertResultDTO> logisticsResult = logistics.insertPackage(logisticsPackage);
        if (logisticsResult != null && logisticsResult.getBody() != null) {
            logisticsId = logisticsResult.getBody().getCodigoRastreio();
        }

        orderService.save(data, payment, logisticsId);
    }
}
