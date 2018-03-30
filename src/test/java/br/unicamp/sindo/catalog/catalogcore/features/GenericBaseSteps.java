package br.unicamp.sindo.catalog.catalogcore.features;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class GenericBaseSteps<T> {

	@Autowired
	protected TestRestTemplate testRestTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity responseEntity; 
	
	protected UUID uuid;
	protected T dto;
	private List<T> dtos;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity responseEntity(){
		return responseEntity;
	}
	
	@SuppressWarnings("rawtypes")
	public void exportResponseEntity(ResponseEntity responseEntity){
		this.responseEntity = responseEntity;
	}

	public HttpStatus httpStatus() {
		return responseEntity.getStatusCode();
	}

	public void exportUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public HttpHeaders headers() {
		return responseEntity.getHeaders();
	}
	
	public TestRestTemplate template(){
		return testRestTemplate;
	}
	
	public void exportDtos(List<T> dtos){
		this.dtos = (List<T>) dtos;
	}
	
	public List<T> dtos(){
		return dtos;
	}
	
	public ObjectMapper mapper(){
		return objectMapper;
	}
}
