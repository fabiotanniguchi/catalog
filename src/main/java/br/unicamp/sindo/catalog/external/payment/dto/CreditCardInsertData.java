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
        String creditCardNumber = data.getCardNumber().trim();

        CreditCardInsertData creditCard = new CreditCardInsertData();
        creditCard.number = Long.parseLong(creditCardNumber);
        creditCard.hasCredit = hasCredit;
        return creditCard;
    }
}
