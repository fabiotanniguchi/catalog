package br.unicamp.sindo.catalog.external.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardInsertData {
    private Long number;
    private Boolean hasCredit;

    public static CreditCardInsertData from(CreditCardPaymentData data, boolean hasCredit) {
        CreditCardInsertData creditCard = new CreditCardInsertData();
        creditCard.number = Long.parseLong(data.getCardNumber().trim());
        creditCard.hasCredit = hasCredit;
        return creditCard;
    }
}
