package br.unicamp.sindo.catalog.external.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerTicketDTO implements Serializable {

    private String TicketId;
    private String ClienteId;
    private String CompraId;
    private String SiteId;
    private Status StatusId;
    private Integer MessageSize;
    private List<MessageDTO> MessagesList;

    public enum Status {
        OPEN, CLOSED, CANCELED;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class MessageDTO implements Serializable {
        private String Timestamp;
        private String Sender;
        private String Message;
    }
}
