package br.unicamp.sindo.catalog.catalogcore.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.unicamp.sindo.catalog.category.Category;
import br.unicamp.sindo.catalog.utils.web.HeaderBuilder;
import cucumber.api.java8.En;

public interface CrudSteps<T> extends En {
	
	ResponseEntity responseEntity();
	void exportResponseEntity(ResponseEntity entity);
	
	HttpHeaders headers();
	void exportUUID(UUID uuid);
	HttpStatus httpStatus();
	TestRestTemplate template();
	void exportDtos(List<T> list);
	List<T> dtos();
	ObjectMapper mapper();
	
	String path();

	@SuppressWarnings("unchecked")
	default void commonSteps() {
		
		Then("server responds \"(\\w+)\"", (String statusCode) -> {
			HttpStatus expectedStatus = HttpStatus.valueOf(Integer.parseInt(statusCode));
			assertEquals(expectedStatus, httpStatus());
		});
		
		Then("there is a \"([\\w-]+)\" Response Header", (String headerName) -> {
			List<String> values = headers().get(headerName);

			assertEquals(1, values.size());
			assertNotNull(values.get(0));
			assertNotEquals(null, values.get(0), "");

			if("Location".equals(headerName)){
				exportUUID(UUID.fromString(values.get(0)));
			}
		});
		
		Then("the list was gotten", () -> {
			try {
				exportDtos(mapper().readValue(mapper().writeValueAsString(responseEntity().getBody()), List.class));
			} catch (IOException e) {
				//TODO improve
				throw new RuntimeException();
			}
			assertNotNull(dtos());
		});
		
		Then("there are elements on list", () -> {
			assertFalse(dtos().isEmpty());
		});
		
		Then("there are no elements on list", () -> {
			assertTrue(dtos().isEmpty());
		});
	}
	
	default void create(T dto) {
		exportResponseEntity(template().exchange(path(),
				HttpMethod.POST,
				new HttpEntity<T>(dto),
				Void.class));
	}
	
	default void update(T dto, UUID uuid){
		exportResponseEntity(template().exchange(path()+"/"+uuid,
				HttpMethod.PUT,
				new HttpEntity<T>(dto),
				Void.class));
	}
	
	default void get(UUID uuid, Class<T> clazz){
		exportResponseEntity(template().exchange(path()+"/"+uuid,
				HttpMethod.GET,
				null, 
				clazz));
	}
	
	default void get(UUID uuid){
		exportResponseEntity(template().exchange(path()+"/"+uuid,
				HttpMethod.GET,
				null, 
				Void.class));
	}
	
	default void delete(UUID uuid){
		exportResponseEntity(template().exchange(path()+"/"+uuid,
				HttpMethod.DELETE,
				null,
				Void.class));
	}
	
	default void get(UUID uuid, String eTag, Class<T> clazz){
		HttpEntity<Category> entity = new HttpEntity<>(HeaderBuilder.init().eTag(eTag).assemble());
		exportResponseEntity(template().exchange(path()+"/"+uuid,
				HttpMethod.GET,
				entity, 
				clazz));
	}

	default void list(MultiValueMap<String, String> parameters, Class<T> clazz){
		URI uri = UriComponentsBuilder.fromUriString(path()).queryParams(parameters).build().encode().toUri();
		exportResponseEntity(template().exchange(uri, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<T>>() {}));
	}
	
	default void undelete(UUID uuid){
		exportResponseEntity(template().exchange(path()+"/"+uuid+":undelete",
				HttpMethod.POST,
				null,
				new ParameterizedTypeReference<T>() {}));
	}

}