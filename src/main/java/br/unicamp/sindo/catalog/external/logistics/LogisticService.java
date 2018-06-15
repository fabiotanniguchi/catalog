package br.unicamp.sindo.catalog.external.logistics;

import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageFromDTO;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageInsertResultDTO;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageToDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class LogisticService {

    public static final String LOGISTICS_API_KEY = "947682d1-7093-596e-9829-90a1845dc8a5";
    private static final String LOGISTICS_HOST = "https://hidden-basin-50728.herokuapp.com";
    private static final String LOGISTICS_COST_CALC_PATH = "/calculafrete";
    private static final String LOGISTICS_TRACKING_PATH = "/rastrearentrega/{cod_rastreio}?apiKey={apiKey}";
    private static final String LOGISTICS_PACKAGE_INSERT_PATH = "/cadastrarentrega";

    private RestTemplate restTemplate = new RestTemplate();


    public ResponseEntity<LogisticsPackageInsertResultDTO> insertPackage(LogisticsPackageToDTO dto) {
        final String uri = LOGISTICS_HOST + LOGISTICS_PACKAGE_INSERT_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LogisticsPackageToDTO> entity = new HttpEntity<>(dto, headers);

        ResponseEntity<LogisticsPackageInsertResultDTO> response = null;
        try {
            response = restTemplate.postForEntity(uri, entity, LogisticsPackageInsertResultDTO.class);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível inserir pacote.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

    public ResponseEntity<LogisticsPackageInsertResultDTO> insertPackage(LogisticsPackageFromDTO dto) {
        final String uri = LOGISTICS_HOST + LOGISTICS_PACKAGE_INSERT_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LogisticsPackageToDTO> entity = new HttpEntity<>(LogisticsPackageToDTO.from(dto), headers);

        ResponseEntity<LogisticsPackageInsertResultDTO> response = null;
        try {
            response = restTemplate.postForEntity(uri, entity, LogisticsPackageInsertResultDTO.class);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível inserir pacote.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }
}
