package br.unicamp.sindo.catalog.external.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardData {

    private CreditCardDataData creditCard;
    private String errorMessage;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class CreditCardDataData {
        private Long number;
        private Boolean hasCredit;
        private String id;
    }
}
