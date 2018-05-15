package br.unicamp.sindo.catalog.external.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer1LoginDTO {
    private String email;
    private String password;
}
