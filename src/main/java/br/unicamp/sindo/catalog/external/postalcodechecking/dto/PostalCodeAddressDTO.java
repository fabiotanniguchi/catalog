package br.unicamp.sindo.catalog.external.postalcodechecking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostalCodeAddressDTO {

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
}
