package br.unicamp.sindo.catalog.catalogcore.features.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.unicamp.sindo.catalog.catalogcore.features.CrudSteps;
import br.unicamp.sindo.catalog.catalogcore.features.GenericBaseSteps;
import br.unicamp.sindo.catalog.category.Category;
import br.unicamp.sindo.catalog.category.CategoryEntity;
import br.unicamp.sindo.catalog.category.CategoryRepository;
import br.unicamp.sindo.catalog.category.Status;

public class CategorySteps extends GenericBaseSteps<Category> implements CrudSteps<Category> {

	@Autowired
	CategoryRepository repository;
	
	private String expectedName = "Name2";

	public CategorySteps(){
		commonSteps();
		
		Given("\"(\\w+)\" existing entities", (String quantity) -> {
			IntStream.range(0, Integer.parseInt(quantity)).forEach(i -> setUpEntity(Status.ACTIVE));
		});
		
		Given("an existing category", () -> {
			setUpEntity(Status.ACTIVE);
		});
		
		Given("a deleted category", () -> {
			setUpEntity(Status.DISABLED);
		});

		When("a category is created", () -> {
			create(Category.builder().name("Name").description("Description").build());
		});
		
		When("an attempt to create an entity with ID is made", () -> {
			create(Category.builder().id(UUID.randomUUID()).name("Name").build());
		});
		
		When("there is an update operation for an existing entity", () -> {
			update(Category.builder().name(expectedName).build(), uuid());
		});
		
		When("there is an update operation for a non existing entity", () -> {
			update(Category.builder().name(expectedName).build(), UUID.randomUUID());
		});
		
		When("there is an inconsistent update operation", () -> {
			update(Category.builder().id(UUID.randomUUID()).name(expectedName).build(), UUID.randomUUID());
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
		
		Then("the category was gotten", () -> {
			assertNotNull(responseEntity().getBody());
		});
		
		Then("category has changed", () -> {
			assertEquals(expectedName.equalsIgnoreCase(repository.findById(uuid()).get().getName()), true);
		});

	}

	public void setUpEntity(Status status){
		CategoryEntity category = CategoryEntity.fromDTO(Category.builder()
				.name("Name")
				.description("Description")
				.status(status)
				.build());
		
		category = repository.save(category);
		exportUUID(category.getId());
		exportDto(category.assemble());
		exportEtag(dto().version());
	}
	
	@Override
	public String path() {
		return "/categories";
	}

}
