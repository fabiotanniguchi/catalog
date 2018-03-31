package br.unicamp.sindo.catalog.catalogcore.features.product;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.unicamp.sindo.catalog.catalogcore.features.CrudSteps;
import br.unicamp.sindo.catalog.catalogcore.features.GenericBaseSteps;
import br.unicamp.sindo.catalog.product.Product;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/products.feature", plugin = { "pretty", "html:target/cucumber"})
public class ProductSteps extends GenericBaseSteps<Product> implements CrudSteps<Product> {

	private String expectedName = "Name2";

	public ProductSteps(){
		commonSteps();
		
		Given("\"(\\w+)\" existing entities", (String quantity) -> {
			IntStream.range(0, Integer.parseInt(quantity)).forEach(i -> {});
		});
		
		Given("an existing product", () -> {
		});
		
		Given("a deleted product", () -> {
		});

		When("a product is created", () -> {
			create(Product.builder().name("Name").build());
		});
		
		When("an attempt to create an entity with ID is made", () -> {
			create(Product.builder().id(UUID.randomUUID()).name("Name").build());
		});
		
		When("there is an update operati on for an existing entity", () -> {
			update(Product.builder().name(expectedName).build(), uuid());
		});
		
		When("there is an update operation for a non existing entity", () -> {
			update(Product.builder().name(expectedName).build(), UUID.randomUUID());
		});
		
		When("there is an inconsistent update operation", () -> {
			update(Product.builder().id(UUID.randomUUID()).name(expectedName).build(), UUID.randomUUID());
		});
		
		When("there is a matching list operation by name", () -> {
			MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
			parameter.add("name", dto().getName());
			list(parameter);
		});
		
		When("there is a non matching list operation by name", () -> {
			MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
			parameter.add("name", UUID.randomUUID().toString());
			list(parameter);
		});
				
		Then("the product was gotten", () -> {
			assertNotNull(responseEntity().getBody());
		});
		
		Then("product has changed", () -> {
		});

	}

	public void setUpEntity(String status){
		
	}
	
	@Override
	public String path() {
		return "/products";
	}

}
