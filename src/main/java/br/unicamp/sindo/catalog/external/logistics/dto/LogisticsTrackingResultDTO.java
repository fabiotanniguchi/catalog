package br.unicamp.sindo.catalog.external.logistics.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogisticsTrackingResultDTO implements Serializable {

    private String status;
    private String idProduto;
    private String tipoEntrega;
    private Long preco;
    private String cepOrigem;
    private String cepDestino;
    private Long peso;
    private String tipoPacote;
    private Long altura;
    private Long largura;
    private Long comprimento;
    private List<LogisticsTrackingResultLogDTO> historicoRastreio;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class LogisticsTrackingResultLogDTO {
        private String hora;
        private String local;
        private String mensagem;
    }
}
