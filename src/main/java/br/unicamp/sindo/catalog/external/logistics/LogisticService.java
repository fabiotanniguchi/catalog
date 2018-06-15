package br.unicamp.sindo.catalog.external.logistics;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageFromDTO;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageInsertResultDTO;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageToDTO;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsTrackingResultDTO;
import br.unicamp.sindo.catalog.ordercopy.Order;

@Service
public class LogisticService {

    public static final String LOGISTICS_API_KEY = "947682d1-7093-596e-9829-90a1845dc8a5";
    private static final String LOGISTICS_HOST = "https://hidden-basin-50728.herokuapp.com";
    private static final String LOGISTICS_COST_CALC_PATH = "/calculafrete";
    private static final String LOGISTICS_TRACKING_PATH = "/rastrearentrega/{cod_rastreio}?apiKey={apiKey}";
    private static final String LOGISTICS_PACKAGE_INSERT_PATH = "/cadastrarentrega";

    private RestTemplate restTemplate = new RestTemplate();


    public LogisticsPackageInsertResultDTO insertPackage(Order order) {
        final String uri = LOGISTICS_HOST + LOGISTICS_PACKAGE_INSERT_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        LogisticsPackageToDTO dto = new LogisticsPackageToDTO(order);

        HttpEntity<LogisticsPackageToDTO> entity = new HttpEntity<>(dto, headers);

        ResponseEntity<LogisticsPackageInsertResultDTO> response = null;
        try {
            response = restTemplate.postForEntity(uri, entity, LogisticsPackageInsertResultDTO.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível inserir pacote.");
            e.printStackTrace();
            return null;
        }
    }

    public LogisticsTrackingResultDTO getStatus(Order order) {
    	final String uri = LOGISTICS_HOST + LOGISTICS_TRACKING_PATH;

        Map<String, String> params = new HashMap<>();
        params.put("apiKey", LOGISTICS_API_KEY);
        params.put("cod_rastreio", order.getPackageTracking().getCodigoRastreio());

        ResponseEntity<LogisticsTrackingResultDTO> response = null;
        try {
            response = restTemplate.getForEntity(uri, LogisticsTrackingResultDTO.class, params);
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível obter dados de rastreio");
            e.printStackTrace();
            return null;
        }
        return response.getBody();
    }
}
