package br.unicamp.sindo.catalog.external.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardPaymentData {

    private String clientCardName;
    private String cpf;
    private String cardNumber;
    private String month;
    private String year;
    private String securityCode;
    private String value;
    private String instalments;

    public static CreditCardPaymentData from(WebsitePaymentData website) {
        CreditCardPaymentData data = new CreditCardPaymentData();
        data.clientCardName = website.getClientCardName();
        data.cpf = website.getCpf();
        data.cardNumber = website.getCardNumber();
        data.month = website.getMonth();
        data.year = website.getYear();
        data.securityCode = website.getSecurityCode();
        data.value = website.getValue().toString();
        data.instalments = website.getInstalments().toString();
        return data;
    }
}
