package br.unicamp.sindo.catalog.external.payment.dto;

import br.unicamp.sindo.catalog.external.logistics.DeliveryType;
import br.unicamp.sindo.catalog.external.payment.PaymentType;
import br.unicamp.sindo.catalog.order.ProductOrigin;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebsitePaymentData {

    private List<OrderProductData> products;

    private String clientCardName;
    private String cpf;
    private String clientAddress;
    private String clientCep;
    private String cardNumber;
    private String month;
    private String year;
    private String securityCode;
    private Double value;
    private Integer instalments;

    private Double deliveryCosts;

    private DeliveryType deliveryType;
    private String deliveryCep;

    private String clientEmail;

    private PaymentType paymentType;
}
