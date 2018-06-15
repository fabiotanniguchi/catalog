package br.unicamp.sindo.catalog.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.unicamp.sindo.catalog.utils.repository.BaseEntity;

@Entity
@Table(name = "store_order")
public class OrderEntity extends BaseEntity {

    private String userId;
    private Double total;
    private String payment;
    private String paymentType;
    private String logistic;
    private String logisticTracking;

    @Column(name = "user_id", nullable = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "total", nullable = false)
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Column(name = "payment")
    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Column(name = "payment_type")
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name = "logistic")
    public String getLogistic() {
        return logistic;
    }

    public void setLogistic(String logistic) {
        this.logistic = logistic;
    }

    @Column(name = "logistic_tracking")
    public String getLogisticTracking() {
        return logisticTracking;
    }

    public void setLogisticTracking(String logisticTracking) {
        this.logisticTracking = logisticTracking;
    }
}
