package br.unicamp.sindo.catalog.ordercopy;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import br.unicamp.sindo.catalog.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Order implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Map<String, Product> products;
    private Payment payment;
    private Address address;
    private User user;
    private int expectedDays;
    private double postalFee;
    private String deliveryType;

    public Order(){}

}
