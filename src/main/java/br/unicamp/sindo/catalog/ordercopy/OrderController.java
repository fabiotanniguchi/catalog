package br.unicamp.sindo.catalog.ordercopy;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.unicamp.sindo.catalog.external.logistics.LogisticService;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageInsertResultDTO;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsTrackingResultDTO;
import br.unicamp.sindo.catalog.external.payment.dto.BoletoPaymentResultData;
import br.unicamp.sindo.catalog.external.payment.dto.BoletoStatusData;
import br.unicamp.sindo.catalog.external.payment.dto.CreditCardPaymentResultData;
import br.unicamp.sindo.catalog.external.payment.rest.PaymentService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    protected OrderRepository orderRepository;;
    
    @Autowired
    protected PaymentService paymentService;
    
    @Autowired
    protected LogisticService logisticsService;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @PostMapping
    public ResponseEntity<Void> post(@RequestBody Order order) throws JsonProcessingException {

    	fillOrderValues(order);
    	
    	boolean pagamentoOk = false;
    	if(order.getPayment() == null){
    		BoletoPaymentResultData billPayment = paymentService.postBillPayment(order);
    		order.setBillResult(billPayment);
    		pagamentoOk = true;
    	}else{
    		CreditCardPaymentResultData creditCardPayment = paymentService.postCreditCardPayment(order);
    		order.setCreditCardResult(creditCardPayment); //creditCardPayment.setResult("UNAUTHORIZED")
    		if("AUTHORIZED".equals(creditCardPayment.getResult())){
    			pagamentoOk = true;
    		}
    	}
    	
    	//se for boleto ou carto autorizado envia pra logística
    	if(pagamentoOk == true){
	    	LogisticsPackageInsertResultDTO packageTracking = logisticsService.insertPackage(order);
	    	order.setPackageTracking(packageTracking);
    	}
    	OrderEntity entity = new OrderEntity();
    	entity.setId(UUID.randomUUID());
    	order.setId(entity.getId());
    	entity.setUserId(order.getUser().getId());
    	entity.setOrderJson(mapper.writeValueAsString(order));
    	orderRepository.save(entity);
    	
    	if(!pagamentoOk){
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}

    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    private void fillOrderValues(Order order) {
    	double subTotal = 0;
		for(Product p: order.getProducts()){
			subTotal += p.getQuantity() * p.getPrice();
		}
		order.setSubTotal(subTotal);
		order.setTotal(subTotal + order.getPostalFee()); 
	}

	@GetMapping
    public ResponseEntity<List<Order>> list(@RequestParam String userId){
    	return new ResponseEntity<List<Order>>(
    			orderRepository.findByUserId(userId)
    			.stream()
    			.sorted(Comparator.comparing(OrderEntity::getCreatedAt).reversed())
    			.map(OrderEntity::assemble)
    			.map(o -> {
    				if(o.getBillResult() != null){
						BoletoStatusData data = paymentService.getBoletoStatus(o.getBillResult().getCode());
						o.setBillStatus(data);
    				}
    				
    				if(o.getPackageTracking() != null){
	    				LogisticsTrackingResultDTO deliveryTracking = logisticsService.getStatus(o);
	    				o.setDeliveryTracking(deliveryTracking);
    				}
    				
    				return o;
    			})
    			.collect(Collectors.toList()), HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
    	orderRepository.deleteAll();
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
