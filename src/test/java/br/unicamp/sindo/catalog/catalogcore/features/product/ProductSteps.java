package br.unicamp.sindo.catalog.catalogcore.features.product;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.unicamp.sindo.catalog.catalogcore.features.CrudSteps;
import br.unicamp.sindo.catalog.catalogcore.features.GenericBaseSteps;
import br.unicamp.sindo.catalog.product.Product;
import br.unicamp.sindo.catalog.product.ProductEntity;
import br.unicamp.sindo.catalog.product.ProductRepository;
import br.unicamp.sindo.catalog.product.Status;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/products.feature", plugin = { "pretty", "html:target/cucumber"})
public class ProductSteps extends GenericBaseSteps<Product> implements CrudSteps<Product> {

	@Autowired
	private ProductRepository repository;
	
	private String expectedName = "Name2";

	public ProductSteps(){
		commonSteps();
		
		Given("\"(\\w+)\" existing entities", (String quantity) -> {
			IntStream.range(0, Integer.parseInt(quantity)).forEach(i -> {});
		});
		
		Given("an existing product", () -> {
			setUpEntity(Status.ACTIVE);
		});
		
		Given("a deleted product", () -> {
			setUpEntity(Status.DISABLED);
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

	public void setUpEntity(Status status){
		ProductEntity product = ProductEntity.fromDTO(Product.builder()
				.name("Name")
				.status(status)
				.build());
		
		product = repository.save(product);
		exportUUID(product.getId());
		exportDto(product.assemble());
		exportEtag(dto().version());
	}
	
	@Override
	public String path() {
		return "/products";
	}

}
