package br.unicamp.sindo.catalog.ordercopy;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageInsertResultDTO;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsTrackingResultDTO;
import br.unicamp.sindo.catalog.external.payment.dto.BoletoPaymentResultData;
import br.unicamp.sindo.catalog.external.payment.dto.BoletoStatusData;
import br.unicamp.sindo.catalog.external.payment.dto.CreditCardPaymentResultData;
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
    private List<Product> products;
    private Payment payment;
    private Address address;
    private User user;
    private int expectedDays;
    private double postalFee;
    private String deliveryType;
    private double subTotal;
    private double total;
    
    private BoletoPaymentResultData billResult;
    private BoletoStatusData billStatus;
    private CreditCardPaymentResultData creditCardResult;

    private LogisticsPackageInsertResultDTO packageTracking;
    private LogisticsTrackingResultDTO deliveryTracking;
    
    public Order(){}

}
