package br.unicamp.sindo.catalog.ordercopy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.unicamp.sindo.catalog.external.logistics.LogisticService;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageFromDTO;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageInsertResultDTO;
import br.unicamp.sindo.catalog.external.logistics.dto.LogisticsPackageToDTO;
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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    protected OrderRepository orderRepository;;
	LogisticService logisticService = new LogisticService();

    private ObjectMapper mapper = new ObjectMapper();
    private RestTemplate restTemplate = new RestTemplate();
    
    @PostMapping
    public ResponseEntity<Void> post(@RequestBody Order order) throws JsonProcessingException {

//		LogisticsPackageToDTO dto = new LogisticsPackageToDTO(order);
//		LogisticsPackageInsertResultDTO resultDto = logisticService.insertPackage(dto).getBody();

		OrderEntity entity = new OrderEntity();
		entity.setUserId(order.getUser().getId());
    	entity.setOrderJson(mapper.writeValueAsString(order));
    	orderRepository.save(entity);

    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Order>> list(@RequestParam String userId){
    	return new ResponseEntity<List<Order>>(
    			orderRepository.findByUserId(userId)
    			.stream()
    			.sorted(Comparator.comparing(OrderEntity::getCreatedAt).reversed())
    			.map(OrderEntity::assemble)
    			.collect(Collectors.toList()), HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
    	orderRepository.deleteAll();
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
