package br.unicamp.sindo.catalog.external.logistics.dto;

import br.unicamp.sindo.catalog.external.logistics.DeliveryType;
import br.unicamp.sindo.catalog.external.logistics.PackageType;
import br.unicamp.sindo.catalog.external.logistics.rest.LogisticsRest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogisticsPackageToDTO {

    private String idProduto;
    private String tipoEntrega;
    private String cepOrigem;
    private String cepDestino;
    private Long peso;
    private String tipoPacote;
    private Long altura;
    private Long largura;
    private Long comprimento;
    private String apiKey;

    public static LogisticsPackageToDTO from(LogisticsPackageFromDTO from) {
        LogisticsPackageToDTO to = new LogisticsPackageToDTO();

        to.idProduto = null;
        to.tipoEntrega = DeliveryType.values()[from.getDeliveryType()].toString();
        to.cepOrigem = from.getFromCEP();
        to.cepDestino = from.getToCEP();
        to.peso = from.getWeight();
        to.tipoPacote = PackageType.values()[from.getPackageType()].toString();
        to.altura = from.getHeight();
        to.largura = from.getWidth();
        to.comprimento = from.getLength();
        to.apiKey = LogisticsRest.LOGISTICS_API_KEY;

        return to;
    }
}
