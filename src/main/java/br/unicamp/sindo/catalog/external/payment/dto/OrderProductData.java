package br.unicamp.sindo.catalog.external.payment.dto;

import br.unicamp.sindo.catalog.order.ProductOrigin;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderProductData {
    private String productId;
    private Long quantity;
    private Double unitPrice;
    private ProductOrigin origin;
}
