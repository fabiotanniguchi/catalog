package br.unicamp.sindo.catalog.external.postalcodechecking.rest;

import br.unicamp.sindo.catalog.external.postalcodechecking.dto.PostalCodeAddressDTO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/external/postalcode")
public class PostalCodeRest {

    private String POSTAL_CODE_API_KEY = "1a2cd962-e70a-4d08-bfa8-2ee127d89e9c";
    private String POSTAL_CODE_API_KEY_NAME = "x-api-key";

    private String POSTAL_CODE_SERV_HOST = "http://node.thiagoelg.com/paises/br";
    private String POSTAL_CODE_SERV_CEP = "/cep/{cep}";

    private RestTemplate restTemplate = new RestTemplate();
    
    @GetMapping(value = "/{cep}")
    public ResponseEntity<PostalCodeAddressDTO> getAddressFromPostalCode(@PathVariable(value = "cep") String cep) {
        final String uri = POSTAL_CODE_SERV_HOST + POSTAL_CODE_SERV_CEP;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(POSTAL_CODE_API_KEY_NAME, POSTAL_CODE_API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(cep, headers);

        Map<String, String> params = new HashMap<>();
        params.put("cep", cep);

        ResponseEntity<PostalCodeAddressDTO> response = null;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, PostalCodeAddressDTO.class, params);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível consultar CEP " + cep);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PostalCodeAddressDTO());
        }

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }
}
