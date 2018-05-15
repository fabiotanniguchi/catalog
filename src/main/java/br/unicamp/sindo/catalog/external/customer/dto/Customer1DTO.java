package br.unicamp.sindo.catalog.external.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer1DTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String samePass;
    private String birthDate;
    private String cpf;
    private String cep;
    private String address;
    private String gender;
    private String telephone;
}
