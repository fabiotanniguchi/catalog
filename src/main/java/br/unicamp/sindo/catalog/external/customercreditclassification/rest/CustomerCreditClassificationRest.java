package br.unicamp.sindo.catalog.external.customercreditclassification.rest;

import br.unicamp.sindo.catalog.external.customercreditclassification.dto.CustomerCreditClassificationDTO;
import br.unicamp.sindo.catalog.external.customercreditclassification.dto.CustomerCreditClassificationPaymentDTO;
import br.unicamp.sindo.catalog.external.customercreditclassification.dto.CustomerCreditClassificationUpdateDTO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/external/credit")
public class CustomerCreditClassificationRest {

    private String CREDIT_API_KEY = "tmvcgp1";
    private String CREDIT_API_KEY_NAME = "x-api-key";

    private String CREDIT_HOST = "https://glacial-brook-98386.herokuapp.com/";
    private String CREDIT_SCORE_PATH = "/score/{cpf}";
    private String CREDIT_PAYMENT_PATH = "/payment/{cpf}";

    @GetMapping(value="/{cpf}")
    public ResponseEntity<CustomerCreditClassificationDTO> getCreditScore(@PathVariable(value="cpf") String cpf){
        final String uri = CREDIT_HOST + CREDIT_SCORE_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CREDIT_API_KEY_NAME, CREDIT_API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(cpf, headers);

        Map<String, String> params = new HashMap<>();
        params.put("cpf", cpf);

        ResponseEntity<CustomerCreditClassificationDTO> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, CustomerCreditClassificationDTO.class, params);
        }
        catch(HttpClientErrorException ex){
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND){ // client without profile at credit institution
                return ResponseEntity.status(HttpStatus.OK).body(new CustomerCreditClassificationDTO());
            }else{
                System.err.println("[ERRO] Não foi possível consultar score do CPF " + cpf);
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerCreditClassificationDTO());
            }
        }
        catch(Exception e){
            System.err.println("[ERRO] Não foi possível consultar score do CPF " + cpf);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerCreditClassificationDTO());
        }

        return response;
    }

    @PostMapping(value="/{cpf}")
    public ResponseEntity<String> putScore(@PathVariable(value="cpf") String cpf, @RequestBody CustomerCreditClassificationUpdateDTO customer){
        final String uri = CREDIT_HOST + CREDIT_SCORE_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CREDIT_API_KEY_NAME, CREDIT_API_KEY);

        HttpEntity<CustomerCreditClassificationUpdateDTO> entity = new HttpEntity<>(customer, headers);

        Map<String, String> params = new HashMap<>();
        params.put("cpf", cpf);

        ResponseEntity<String> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class, params);
        }catch(Exception e){
            System.err.println("[ERRO] Não foi possível inserir score do CPF " + cpf);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String());
        }

        return response;
    }

    @PostMapping(value="/payment/{cpf}")
    public ResponseEntity<String> putPayment(@PathVariable(value="cpf") String cpf, @RequestBody CustomerCreditClassificationPaymentDTO customer){
        final String uri = CREDIT_HOST + CREDIT_PAYMENT_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CREDIT_API_KEY_NAME, CREDIT_API_KEY);

        HttpEntity<CustomerCreditClassificationPaymentDTO> entity = new HttpEntity<>(customer, headers);

        Map<String, String> params = new HashMap<>();
        params.put("cpf", cpf);

        ResponseEntity<String> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class, params);
        }catch(Exception e){
            System.err.println("[ERRO] Não foi possível inserir pagamento do CPF " + cpf);
            e.printStackTrace();
        }

        return response;
    }
}
