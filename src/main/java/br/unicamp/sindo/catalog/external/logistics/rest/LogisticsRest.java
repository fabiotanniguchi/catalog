package br.unicamp.sindo.catalog.external.logistics.rest;

import br.unicamp.sindo.catalog.external.logistics.DeliveryType;
import br.unicamp.sindo.catalog.external.logistics.PackageType;
import br.unicamp.sindo.catalog.external.logistics.dto.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/external/logistics")
public class LogisticsRest {

    public static final String LOGISTICS_API_KEY = "947682d1-7093-596e-9829-90a1845dc8a5";
    private static final String LOGISTICS_HOST = "https://hidden-basin-50728.herokuapp.com";
    private static final String LOGISTICS_COST_CALC_PATH = "/calculafrete";
    private static final String LOGISTICS_TRACKING_PATH = "/rastrearentrega/{cod_rastreio}?apiKey={apiKey}";
    private static final String LOGISTICS_PACKAGE_INSERT_PATH = "/cadastrarentrega";

    @GetMapping(value = "/calc")
    public ResponseEntity<LogisticsCostCalcResultDTO> getCalc(@RequestBody LogisticsCostCalcDTO dto) {
        final String uri = LOGISTICS_HOST + LOGISTICS_COST_CALC_PATH;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("tipoEntrega", DeliveryType.values()[dto.getDeliveryType()].toString())
                .queryParam("cepOrigem", dto.getFromCEP())
                .queryParam("cepDestino", dto.getToCEP())
                .queryParam("peso", dto.getWeight().toString())
                .queryParam("tipoPacote", PackageType.values()[dto.getPackageType()].toString())
                .queryParam("comprimento", dto.getLength().toString())
                .queryParam("altura", dto.getHeight().toString())
                .queryParam("largura", dto.getWidth().toString());

        ResponseEntity<LogisticsCostCalcResultDTO> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.getForEntity(builder.toUriString(), LogisticsCostCalcResultDTO.class);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível obter cálculo do frete");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @GetMapping(value = "/tracking/{code}")
    public ResponseEntity<LogisticsTrackingResultDTO> getTrackingInfo(@PathVariable String code) {
        final String uri = LOGISTICS_HOST + LOGISTICS_TRACKING_PATH;

        Map<String, String> params = new HashMap<>();
        params.put("apiKey", LOGISTICS_API_KEY);
        params.put("cod_rastreio", code);

        ResponseEntity<LogisticsTrackingResultDTO> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.getForEntity(uri, LogisticsTrackingResultDTO.class, params);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível obter dados de rastreio");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<LogisticsPackageInsertResultDTO> insertPackage(@RequestBody LogisticsPackageFromDTO dto) {
        final String uri = LOGISTICS_HOST + LOGISTICS_PACKAGE_INSERT_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LogisticsPackageToDTO> entity = new HttpEntity<>(LogisticsPackageToDTO.from(dto), headers);

        ResponseEntity<LogisticsPackageInsertResultDTO> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.postForEntity(uri, entity, LogisticsPackageInsertResultDTO.class);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível inserir pacote.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }
}
