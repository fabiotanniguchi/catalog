package br.unicamp.sindo.catalog.external.customercreditclassification.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerCreditClassificationPaymentDTO {

    private Integer total_paid;
    private Integer total_value;
}
