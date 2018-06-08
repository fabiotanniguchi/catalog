package br.unicamp.sindo.catalog.external.logistics.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogisticsCostCalcDTO {

    private Byte deliveryType;
    private String fromCEP;
    private String toCEP;
    private Long weight;
    private Byte packageType;
    private Long length;
    private Long height;
    private Long width;
}
