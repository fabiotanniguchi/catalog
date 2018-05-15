package br.unicamp.sindo.catalog.external.customer.rest;

import br.unicamp.sindo.catalog.external.customer.dto.Customer1DTO;
import br.unicamp.sindo.catalog.external.customer.dto.Customer1LoginDTO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/external/customers")
public class Customer1Rest {

    private static final String CUSTOMER1_HOST = "https://us-central1-first-try-18f38.cloudfunctions.net/clientsAPI/";

    private static final String CUSTOMER1_API_KEY_NAME = "api_key";
    private static final String CUSTOMER1_API_KEY = "07cb5953492685994da425f83ac53657";

    private static final String CUSTOMER1_INSERT_PATH = "register";
    private static final String CUSTOMER1_UPDATE_PATH = "update/{id}";
    private static final String CUSTOMER1_GETDATA_PATH = "clients/{id}";

    private static final String CUSTOMER1_CHGPASSWRD_PATH = "changePass/{id}";

    private static final String CUSTOMER1_LOGIN_PATH = "login";

    @PostMapping
    public ResponseEntity<Void> insertCustomer1(@RequestBody Customer1DTO customer){
        final String uri = CUSTOMER1_HOST + CUSTOMER1_INSERT_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CUSTOMER1_API_KEY_NAME, CUSTOMER1_API_KEY);

        HttpEntity<Customer1DTO> entity = new HttpEntity<>(customer, headers);

        ResponseEntity<Void> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(uri, HttpMethod.POST, entity, Void.class);
            HttpStatus status = response.getStatusCode();

            System.out.println(status);
        }catch(Exception e){
            System.err.println("[ERRO] Não foi possível inserir cliente " + customer.getEmail());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return response;
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Void> updateCustomer1(@PathVariable(value="id") String id, @RequestBody Customer1DTO customer){
        final String uri = CUSTOMER1_HOST + CUSTOMER1_UPDATE_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CUSTOMER1_API_KEY_NAME, CUSTOMER1_API_KEY);

        HttpEntity<Customer1DTO> entity = new HttpEntity<>(customer, headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        ResponseEntity<Void> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Void.class, params);
        }catch(Exception e){
            System.err.println("[ERRO] Não foi possível atualizar cliente " + customer.getEmail());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return response;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Void> getCustomer1(@PathVariable(value="id") String id, @RequestBody Customer1DTO customer){
        final String uri = CUSTOMER1_HOST + CUSTOMER1_GETDATA_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CUSTOMER1_API_KEY_NAME, CUSTOMER1_API_KEY);

        HttpEntity<Customer1DTO> entity = new HttpEntity<>(customer, headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        ResponseEntity<Void> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, Void.class, params);
        }catch(Exception e){
            System.err.println("[ERRO] Não foi possível atualizar cliente " + customer.getEmail());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return response;
    }

    @GetMapping(value="/login")
    public ResponseEntity<Void> loginCustomer1(@RequestBody Customer1LoginDTO customer){
        final String uri = CUSTOMER1_HOST + CUSTOMER1_LOGIN_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CUSTOMER1_API_KEY_NAME, CUSTOMER1_API_KEY);

        HttpEntity<Customer1LoginDTO> entity = new HttpEntity<>(customer, headers);

        ResponseEntity<Void> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, Void.class);
        }catch(Exception e){
            System.err.println("[ERRO] Não foi possível atualizar cliente " + customer.getEmail());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return response;
    }
}
