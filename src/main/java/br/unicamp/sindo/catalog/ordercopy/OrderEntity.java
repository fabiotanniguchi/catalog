package br.unicamp.sindo.catalog.ordercopy;

import br.unicamp.sindo.catalog.utils.repository.BaseEntity;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "store_order2")
public class OrderEntity extends BaseEntity {

    private String userId;
    private String orderJson;
    
    @Transient
    private ObjectMapper mapper = new ObjectMapper();

    @Column(name = "user_id", nullable = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Column(name = "order_json", columnDefinition = "text")
    public String getOrderJson() {
    	return orderJson;
    }
    
    public void setOrderJson(String orderJson){
    	this.orderJson = orderJson;
    }
    
    public Order assemble(){
    	try {
    		Order order = mapper.readValue(orderJson, Order.class);
    		order.setId(id);
			return mapper.readValue(orderJson, Order.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

}
