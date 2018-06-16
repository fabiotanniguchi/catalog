package br.unicamp.sindo.catalog.external.payment.rest;

import java.util.HashMap;
import java.util.Map;

import br.unicamp.sindo.catalog.external.customercreditclassification.dto.CustomerCreditClassificationDTO;
import br.unicamp.sindo.catalog.external.customercreditclassification.rest.CustomerCreditClassificationRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.unicamp.sindo.catalog.external.payment.dto.BoletoPaymentData;
import br.unicamp.sindo.catalog.external.payment.dto.BoletoPaymentResultData;
import br.unicamp.sindo.catalog.external.payment.dto.BoletoStatusData;
import br.unicamp.sindo.catalog.external.payment.dto.CreditCardData;
import br.unicamp.sindo.catalog.external.payment.dto.CreditCardInsertData;
import br.unicamp.sindo.catalog.external.payment.dto.CreditCardPaymentData;
import br.unicamp.sindo.catalog.external.payment.dto.CreditCardPaymentResultData;
import br.unicamp.sindo.catalog.ordercopy.Order;

@Service
public class PaymentService {

    private static final String OUR_CEP = "13070712";
    private static final String PAYMENTS_HOST = "https://payment-server-mc851.herokuapp.com/";
    private static final String EVALUATE_CREDIT_CARD_PAYMENT_PATH = "payments/creditCard";
    private static final String SUBMIT_BOLETO_PAYMENT_PATH = "payments/bankTicket";
    private static final String BOLETO_STATUS_PATH = "payments/bankTicket/{code}/status";
    private static final String CREDIT_CARD_PATH = "creditCard";

    @Autowired
    protected CustomerCreditClassificationRest cccr;

    public BoletoStatusData getBoletoStatus(String code) {
        final String uri = PAYMENTS_HOST + BOLETO_STATUS_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> params = new HashMap<>();
        params.put("code", code);

        ResponseEntity<BoletoStatusData> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.getForEntity(uri, BoletoStatusData.class, params);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível obter dados de pagamento do boleto");
            e.printStackTrace();
            throw new RuntimeException();
        }
        return response.getBody();
    }

    public CreditCardPaymentResultData postCreditCardPayment(Order order){
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    	final String uri = PAYMENTS_HOST + EVALUATE_CREDIT_CARD_PAYMENT_PATH;

    	String month = order.getPayment().getExpirationDate().split("/")[0];
    	String year = order.getPayment().getExpirationDate().split("/")[1];
    	
        CreditCardPaymentData data = new CreditCardPaymentData();
        data.setCardNumber(order.getPayment().getCreditCard());
        data.setClientCardName(order.getPayment().getName());
        data.setCpf(order.getUser().getCpf());
        data.setInstalments("1");
        data.setMonth(month);
        data.setSecurityCode(order.getPayment().getCvv());
        data.setValue(String.valueOf(order.getTotal()));
        data.setYear(year);

        boolean hasCredit = false;
        CustomerCreditClassificationDTO dto = cccr.getCreditScore(data.getCpf()).getBody();
        if(dto.getCpf() == null || dto.getCpf().toString().length() < 11 || dto.getScore() > 500){
            hasCredit = true;
        }

        if(data.getCardNumber().startsWith("9911")){
        	hasCredit = false;
        }
        this.tryToInsertCreditCard(data, hasCredit);

        HttpEntity<CreditCardPaymentData> entity = new HttpEntity<>(data, headers);

        ResponseEntity<CreditCardPaymentResultData> response = null;
        RestTemplate restTemplate = new RestTemplate();
        data.setCpf(data.getCpf().replace(".", "").replace(" ","").replace("-",""));
        response = restTemplate.exchange(uri, HttpMethod.POST, entity, CreditCardPaymentResultData.class);
        return response.getBody();
    }
    
    public BoletoPaymentResultData postBillPayment(Order order){
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    	final String uri = PAYMENTS_HOST + SUBMIT_BOLETO_PAYMENT_PATH;

        BoletoPaymentData data = new BoletoPaymentData();
        data.setAddress(order.getUser().getAddress());
        data.setCep(order.getAddress().getPostalCode());
        data.setCpf(order.getUser().getCpf());
        data.setValue(String.valueOf(order.getTotal()));

        HttpEntity<BoletoPaymentData> entity = new HttpEntity<>(data, headers);

        ResponseEntity<BoletoPaymentResultData> response = null;
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.postForEntity(uri, entity, BoletoPaymentResultData.class);
        return response.getBody();
    }
    
//    @PostMapping
//    public ResponseEntity<String> postPayment(@RequestBody WebsitePaymentData paymentData) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        if (paymentData.getPaymentType() == PaymentType.CREDIT_CARD) {
//            final String uri = PAYMENTS_HOST + EVALUATE_CREDIT_CARD_PAYMENT_PATH;
//
//            CreditCardPaymentData data = CreditCardPaymentData.from(paymentData);
//
//            this.tryToInsertCreditCard(data);
//
//            HttpEntity<CreditCardPaymentData> entity = new HttpEntity<>(data, headers);
//
//            ResponseEntity<CreditCardPaymentResultData> response = null;
//            RestTemplate restTemplate = new RestTemplate();
//            response = restTemplate.postForEntity(uri, entity, CreditCardPaymentResultData.class);
//
//            if (response.getBody() != null) {
//                if (StringUtils.isEmpty(response.getBody().getErrorMessage())) {
////                    persistOrder(paymentData, response.getBody());
//                    return ResponseEntity.ok("");
//                } else { // error
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//                }
//            } else { // error
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//            }
//        } else { // BOLETO
//            final String uri = PAYMENTS_HOST + SUBMIT_BOLETO_PAYMENT_PATH;
//
//            BoletoPaymentData data = BoletoPaymentData.from(paymentData);
//
//            HttpEntity<BoletoPaymentData> entity = new HttpEntity<>(data, headers);
//
//            ResponseEntity<BoletoPaymentResultData> response = null;
//            RestTemplate restTemplate = new RestTemplate();
//            response = restTemplate.postForEntity(uri, entity, BoletoPaymentResultData.class);
//
//            if (response.getBody() != null) {
//                if (StringUtils.isEmpty(response.getBody().getErrorMessage())) {
////                    persistOrder(paymentData, response.getBody());
//                    return ResponseEntity.ok("");
//                } else { // error
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//                }
//            } else { // error
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//            }
//        }
//    }

    private void tryToInsertCreditCard(CreditCardPaymentData creditCardPaymentData, boolean hasCredit) {
        final String uri = PAYMENTS_HOST + CREDIT_CARD_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreditCardInsertData> entity = new HttpEntity<>(CreditCardInsertData.from(creditCardPaymentData, hasCredit), headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreditCardData> response = null;;
        try {
            response = restTemplate.postForEntity(uri, entity, CreditCardData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void persistOrder(WebsitePaymentData data, CreditCardPaymentResultData payment) {
//
//        LogisticsPackageFromDTO logisticsPackage = new LogisticsPackageFromDTO();
//        logisticsPackage.setDeliveryType((byte) data.getDeliveryType().ordinal());
//        logisticsPackage.setFromCEP(OUR_CEP);
//        logisticsPackage.setToCEP(data.getDeliveryCep());
//        logisticsPackage.setPackageType((byte) PackageType.BOX.ordinal());
//
//        double length = 0.0;
//        double height = 0.0;
//        double width = 0.0;
//        double weight = 0.0;
//        for (OrderProductData product : data.getProducts()) {
//            double productLength = 1.0;
//            double productHeight = 1.0;
//            double productWidth = 1.0;
//            double productWeight = 1.0;
//
//            Product p = productService.fetch(UUID.fromString(product.getProductId()));
//            if (p != null) {
//                if (p.getHeight() != null) {
//                    productHeight = p.getHeight();
//                }
//                if (p.getLength() != null) {
//                    productLength = p.getLength();
//                }
//                if (p.getWidth() != null) {
//                    productWidth = p.getWidth();
//                }
//                if (p.getWeight() != null) {
//                    productWeight = p.getWeight();
//                }
//
//                weight += productWeight;
//                length += productLength;
//                width += productWidth;
//                height += productHeight;
//            }
//        }
//
//        logisticsPackage.setWeight((long) Math.ceil(weight * 100));
//        logisticsPackage.setWidth((long) Math.ceil(width * 100));
//        logisticsPackage.setLength((long) Math.ceil(length * 100));
//        logisticsPackage.setHeight((long) Math.ceil(height * 100));
//
//        String logisticsId = "ERROR";
//        ResponseEntity<LogisticsPackageInsertResultDTO> logisticsResult = logistics.insertPackage(logisticsPackage);
//        if (logisticsResult != null && logisticsResult.getBody() != null) {
//            logisticsId = logisticsResult.getBody().getCodigoRastreio();
//        }
//
//        orderService.save(data, payment, logisticsId);
//    }
//
//    private void persistOrder(WebsitePaymentData data, BoletoPaymentResultData payment) {
//
//        LogisticsPackageFromDTO logisticsPackage = new LogisticsPackageFromDTO();
//        logisticsPackage.setDeliveryType((byte) data.getDeliveryType().ordinal());
//        logisticsPackage.setFromCEP(OUR_CEP);
//        logisticsPackage.setToCEP(data.getDeliveryCep());
//        logisticsPackage.setPackageType((byte) PackageType.BOX.ordinal());
//
//        double length = 0.0;
//        double height = 0.0;
//        double width = 0.0;
//        double weight = 0.0;
//        for (OrderProductData product : data.getProducts()) {
//            double productLength = 1.0;
//            double productHeight = 1.0;
//            double productWidth = 1.0;
//            double productWeight = 1.0;
//
//            Product p = productService.fetch(UUID.fromString(product.getProductId()));
//            if (p != null) {
//                if (p.getHeight() != null) {
//                    productHeight = p.getHeight();
//                }
//                if (p.getLength() != null) {
//                    productLength = p.getLength();
//                }
//                if (p.getWidth() != null) {
//                    productWidth = p.getWidth();
//                }
//                if (p.getWeight() != null) {
//                    productWeight = p.getWeight();
//                }
//
//                weight += productWeight;
//                length += productLength;
//                width += productWidth;
//                height += productHeight;
//            }
//        }
//
//        logisticsPackage.setWeight((long) Math.ceil(weight * 100));
//        logisticsPackage.setWidth((long) Math.ceil(width * 100));
//        logisticsPackage.setLength((long) Math.ceil(length * 100));
//        logisticsPackage.setHeight((long) Math.ceil(height * 100));
//
//        String logisticsId = "ERROR";
//        ResponseEntity<LogisticsPackageInsertResultDTO> logisticsResult = logistics.insertPackage(logisticsPackage);
//        if (logisticsResult != null && logisticsResult.getBody() != null) {
//            logisticsId = logisticsResult.getBody().getCodigoRastreio();
//        }
//
//        orderService.save(data, payment, logisticsId);
//    }
}
