package br.unicamp.sindo.catalog.ordercopy;

import br.unicamp.sindo.catalog.utils.repository.BaseEntity;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "store_order2")
public class OrderEntity extends BaseEntity {

    private String userId;
    private String orderJson;
    
    @Transient
    private static ObjectMapper mapper = new ObjectMapper();

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
    
    public OrderEntity(Order order){
    	super();
    	setId(order.getId());
    	setUserId(order.getUser().getId());
    	try {
			setOrderJson(mapper.writeValueAsString(order));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public OrderEntity(){}
    
    @PrePersist
    private void flush() {
        setCreatedAt(new Date());
    }
    
    @PreUpdate
    private void flush2(){
    	setUpdatedAt(new Date());
    }

}
