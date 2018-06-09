package br.unicamp.sindo.catalog.external.customer.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import br.unicamp.sindo.catalog.external.customer.dto.Customer1ChangePasswordDTO;
import br.unicamp.sindo.catalog.external.customer.dto.Customer1DTO;
import br.unicamp.sindo.catalog.external.customer.dto.Customer1LoginDTO;

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
    
    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<String> insertCustomer1(@RequestBody Customer1DTO customer) {
        final String uri = CUSTOMER1_HOST + CUSTOMER1_INSERT_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CUSTOMER1_API_KEY_NAME, CUSTOMER1_API_KEY);

        HttpEntity<Customer1DTO> entity = new HttpEntity<>(customer, headers);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
            HttpStatus status = response.getStatusCode();

            System.out.println(status);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível inserir cliente " + customer.getEmail());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateCustomer1(@PathVariable(value = "id") String id, @RequestBody Customer1DTO customer) {
        final String uri = CUSTOMER1_HOST + CUSTOMER1_UPDATE_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CUSTOMER1_API_KEY_NAME, CUSTOMER1_API_KEY);

        HttpEntity<Customer1DTO> entity = new HttpEntity<>(customer, headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class, params);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível atualizar cliente " + customer.getEmail());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getCustomer1(@PathVariable(value = "id") String id, @RequestBody Customer1DTO customer) {
        return callUserDetails(id, customer);
    }

    private ResponseEntity<String> callUserDetails(String id, Customer1DTO customer) {
        final String uri = CUSTOMER1_HOST + CUSTOMER1_GETDATA_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CUSTOMER1_API_KEY_NAME, CUSTOMER1_API_KEY);

        HttpEntity<Customer1DTO> entity = new HttpEntity<>(customer, headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class, params);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível atualizar cliente ");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

    @GetMapping(value = "/login")
    public ResponseEntity<String> loginCustomer1(@RequestParam String email, @RequestParam String password) {
        final String uri = CUSTOMER1_HOST + CUSTOMER1_LOGIN_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CUSTOMER1_API_KEY_NAME, CUSTOMER1_API_KEY);

        headers.add("email", email);
        headers.add("password", password);

        Customer1LoginDTO customer = new Customer1LoginDTO();
        customer.setEmail(email);
        customer.setPassword(password);

        HttpEntity<Customer1LoginDTO> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            response = callUserDetails(response.getBody(), null);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível realizar login do cliente " + email);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return new ResponseEntity<String>(Base64.encode(response.getBody().getBytes()), null, HttpStatus.OK);
    }

    @PutMapping(value = "/change/{id}")
    public ResponseEntity<String> changePasswordCustomer1(@PathVariable(value = "id") String id, @RequestBody Customer1ChangePasswordDTO customer) {
        final String uri = CUSTOMER1_HOST + CUSTOMER1_CHGPASSWRD_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(CUSTOMER1_API_KEY_NAME, CUSTOMER1_API_KEY);

        HttpEntity<Customer1ChangePasswordDTO> entity = new HttpEntity<>(customer, headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class, params);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível atualizar cliente " + id);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }
}
