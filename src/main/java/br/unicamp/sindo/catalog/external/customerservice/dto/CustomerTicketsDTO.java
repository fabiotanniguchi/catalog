package br.unicamp.sindo.catalog.external.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerTicketsDTO implements Serializable {

    private Integer TicketSize;
    private List<CustomerTicketDTO> TicketsList;
}
