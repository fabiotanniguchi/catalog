package br.unicamp.sindo.catalog.external.logistics.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogisticsPackageInsertResultDTO {

    private String status;
    private String codigoRastreio;
}
