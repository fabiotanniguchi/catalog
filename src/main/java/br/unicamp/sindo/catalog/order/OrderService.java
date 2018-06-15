package br.unicamp.sindo.catalog.order;

import br.unicamp.sindo.catalog.external.payment.dto.BoletoPaymentResultData;
import br.unicamp.sindo.catalog.external.payment.dto.CreditCardPaymentResultData;
import br.unicamp.sindo.catalog.external.payment.dto.OrderProductData;
import br.unicamp.sindo.catalog.external.payment.dto.WebsitePaymentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepositoryDeprecated orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public OrderProductEntity save(OrderProductData data, UUID orderId) {
        OrderProductEntity product = new OrderProductEntity();

        product.setOrderId(orderId);
        product.setProductId(UUID.fromString(data.getProductId()));
        product.setProductOrigin(data.getOrigin().ordinal());
        product.setQty(data.getQuantity());
        product.setUnitPrice(data.getUnitPrice());
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());

        return orderProductRepository.save(product);
    }

    public OrderEntity save(WebsitePaymentData data, CreditCardPaymentResultData payment, String logisticsId) {
        OrderEntity order = new OrderEntity();

        double total = 0.0;
        for (OrderProductData product : data.getProducts()) {
            total += product.getUnitPrice() * product.getQuantity();
        }
        total += data.getDeliveryCosts();
        order.setTotal(total);

        order.setPayment(payment.getOperationHash());
        order.setPaymentType("CREDITCARD:" + data.getCardNumber());
        order.setLogistic(logisticsId);
        order.setLogisticTracking(logisticsId);
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        order.setUserId(data.getClientEmail());

        order = orderRepository.save(order);

        for (OrderProductData product : data.getProducts()) {
            this.save(product, order.getId());
        }

        return order;
    }

    public OrderEntity save(WebsitePaymentData data, BoletoPaymentResultData payment, String logisticsId) {
        OrderEntity order = new OrderEntity();

        double total = 0.0;
        for (OrderProductData product : data.getProducts()) {
            total += product.getUnitPrice() * product.getQuantity();
        }
        total += data.getDeliveryCosts();
        order.setTotal(total);

        order.setPayment(payment.getDocumentRep());
        order.setPaymentType("BOLETO:" + payment.getCode());
        order.setLogistic(logisticsId);
        order.setLogisticTracking(logisticsId);
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        order.setUserId(data.getClientEmail());

        order = orderRepository.save(order);

        for (OrderProductData product : data.getProducts()) {
            this.save(product, order.getId());
        }

        return order;
    }
}
