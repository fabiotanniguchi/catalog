package br.unicamp.sindo.catalog.external.logistics.rest;

import br.unicamp.sindo.catalog.external.logistics.LogisticService;
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

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "/calc")
    public ResponseEntity<LogisticsCostCalcResultDTO> getCalc(
            @RequestParam(value = "tipoEntrega") String tipoEntrega,
            @RequestParam(value = "cepDestino") String cepDestino,
            @RequestParam(value = "quantidade") Integer quantidade
    ) {
        final String uri = LOGISTICS_HOST + LOGISTICS_COST_CALC_PATH;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("tipoEntrega", tipoEntrega)
                .queryParam("cepOrigem", "13080-655")
                .queryParam("cepDestino", cepDestino)
                .queryParam("peso", 2 * quantidade)
                .queryParam("tipoPacote", "Caixa")
                .queryParam("comprimento", 20)
                .queryParam("altura", 30)
                .queryParam("largura", 30);

        ResponseEntity<LogisticsCostCalcResultDTO> response = null;
        try {
            response = restTemplate.getForEntity(builder.toUriString(), LogisticsCostCalcResultDTO.class);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível obter cálculo do frete");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

}
