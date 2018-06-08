package br.unicamp.sindo.catalog.external.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoletoPaymentResultData {

    private String code;
    private String documentRep;
    private String result;
    private String detail;
    private String errorMessage;
}
