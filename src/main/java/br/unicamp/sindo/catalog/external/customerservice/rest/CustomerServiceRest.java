package br.unicamp.sindo.catalog.external.customerservice.rest;

import br.unicamp.sindo.catalog.external.customerservice.dto.CustomerTicketDTO;
import br.unicamp.sindo.catalog.external.customerservice.dto.CustomerTicketsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/external/sac")
public class CustomerServiceRest {

    private static final String CUSTOMER_SERVICE_HOST = "https://centralatendimento-mc857.azurewebsites.net/";

    private static final String OUR_SITEID = "c12cce3b9938ca5e410d7347e28d10d0df7869ad";

    private static final String SITE_CUSTOMER_TICKETS_PATH = "tickets/" + OUR_SITEID + "/{customer}";
    private static final String SITE_CUSTOMER_ORDER_TICKETS_PATH = "tickets/" + OUR_SITEID + "/{customer}/compra/{order}";
    private static final String SITE_CUSTOMER_TICKET_PATH = "tickets/" + OUR_SITEID + "/{customer}/ticket/{ticket}";
    private static final String SITE_CUSTOMER_TICKET_CODE_PATH = "tickets/" + OUR_SITEID + "/{customer}/ticket/{ticket}?code={code}";

    @PostMapping
    public ResponseEntity<String> createCustomerTicket(@RequestBody CustomerTicketDTO ticketDTO){

        return null;
    }

    @PostMapping(value="/order")
    public ResponseEntity<String> createCustomerOrderTicket(@RequestBody CustomerTicketDTO ticketDTO){

        return null;
    }

    @GetMapping(value="/{customer}")
    public ResponseEntity<CustomerTicketsDTO> getCustomerTickets(@PathVariable(value="customer") String customer){

        return null;
    }

    @GetMapping(value="/{customer}/order/{order}")
    public ResponseEntity<CustomerTicketsDTO> getCustomerOrderTickets(@PathVariable(value="customer") String customer, @PathVariable(value="order") String order){

        return null;
    }

    @GetMapping(value="/{customer}/ticket/{ticket}")
    public ResponseEntity<CustomerTicketsDTO> getCustomerTicket(@PathVariable(value="customer") String customer, @PathVariable(value="ticket") String ticket){

        return null;
    }

}
