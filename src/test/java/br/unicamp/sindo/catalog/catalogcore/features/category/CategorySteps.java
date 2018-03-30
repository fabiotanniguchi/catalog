package br.unicamp.sindo.catalog.catalogcore.features.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.unicamp.sindo.catalog.catalogcore.features.CrudSteps;
import br.unicamp.sindo.catalog.catalogcore.features.GenericBaseSteps;
import br.unicamp.sindo.catalog.category.Category;
import br.unicamp.sindo.catalog.category.CategoryEntity;
import br.unicamp.sindo.catalog.category.CategoryRepository;

public class CategorySteps extends GenericBaseSteps<Category> implements CrudSteps<Category> {

	@Autowired
	CategoryRepository repository;
	
	private String expectedName = "Name2";

	public CategorySteps(){
		commonSteps();
		
		Given("an existing category", () -> {
			setUpEntity();
		});

		When("a category is created", () -> {
			create(Category.builder().name("Name").description("Description").build());
		});
		
		When("an attempt to create a category with ID is made", () -> {
			create(Category.builder().id(UUID.randomUUID()).name("Name").build());
		});
		
		When("there is an update operation for an existing entity", () -> {
			update(Category.builder().name(expectedName).build(), uuid);
		});
		
		When("there is an update operation for a non existing entity", () -> {
			update(Category.builder().name(expectedName).build(), UUID.randomUUID());
		});
		
		When("there is an inconsistent update operation", () -> {
			update(Category.builder().id(UUID.randomUUID()).name(expectedName).build(), UUID.randomUUID());
		});
		
		When("there is a get operation for an existing resource", () -> {
			get(uuid, Category.class);
		});
		
		When("there is a get operation for a non existing resource", () -> {
			get(UUID.randomUUID());
		});
		
		When("there is a latest-version-etagged get operation", () -> {
			get(uuid, dto.version(), Category.class);
		});

		When("there is a older-version-etagged get operation", () -> {
			get(uuid, "whatever", Category.class);
		});
		
		When("there is a matching list operation by name", () -> {
			MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
			parameter.add("name", dto.getName());
			list(parameter, Category.class);
		});
		
		When("there is a non matching list operation by name", () -> {
			MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
			parameter.add("name", UUID.randomUUID().toString());
			list(parameter, Category.class);
		});
		
		When("there is a page \"(\\w+)\" list operation", (String page) -> {
			MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
			parameter.add("page", page);
			list(parameter, Category.class);
		});
		
		Then("the category was gotten", () -> {
			assertNotNull(responseEntity().getBody());
		});
		
		Then("category has changed", () -> {
			assertEquals(expectedName.equalsIgnoreCase(repository.findById(uuid).get().getName()), true);
		});

	}

	public void setUpEntity(){
		CategoryEntity category = CategoryEntity.fromDTO(Category.builder()
				.name("Name")
				.description("Description")
				.build());
		
		category = repository.save(category);
		uuid = category.getId();
		dto = category.assemble();
	}
	
	@Override
	public String path() {
		return "/categories";
	}

}
