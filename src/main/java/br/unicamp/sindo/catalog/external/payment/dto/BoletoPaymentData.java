package br.unicamp.sindo.catalog.external.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoletoPaymentData {

    private String clientName;
    private String cpf;
    private String address;
    private String cep;
    private String value;

    public static BoletoPaymentData from(WebsitePaymentData data) {
        BoletoPaymentData payment = new BoletoPaymentData();

        payment.clientName = data.getClientCardName();
        payment.cpf = data.getCpf();
        payment.address = data.getClientAddress();
        payment.cep = data.getClientCep();

        Double total = 0.0;
        for (OrderProductData product : data.getProducts()) {
            total += product.getQuantity() * product.getUnitPrice();
        }
        total += data.getDeliveryCosts();

        payment.value = total.toString();

        return payment;
    }
}
