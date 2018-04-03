package br.unicamp.sindo.catalog.category;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.unicamp.sindo.catalog.error.BadParameterException;
import br.unicamp.sindo.catalog.utils.web.ETaggedResponseEntity;
import br.unicamp.sindo.catalog.utils.web.PageableResponseEntity;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	@Autowired
	CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<Category>> list(@RequestParam(required = false) String name
			, @RequestParam(name="parent_category", required = false) UUID parentId
			, @RequestParam(defaultValue = "1") Integer page) {
		int pageSize = 50;
		return PageableResponseEntity.ok(service.list(name, parentId, page, pageSize+1), page, pageSize);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> get(@PathVariable(name = "id") String uuid,
			@RequestHeader(name = "ETag", required = false) String etag){
		Category category = service.fetch(UUID.fromString(uuid));
		return ETaggedResponseEntity.ok(category, etag);
	}
	
	@PostMapping
	public ResponseEntity<Void> post(@RequestBody Category category){
		validate(category);
		category = service.save(category);
		return ETaggedResponseEntity.created(category);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> put(@PathVariable(name = "id") UUID uuid,
			@RequestBody Category category){
		validate(uuid, category);
		category = service.update(uuid, category);
		return ETaggedResponseEntity.updated(category);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID uuid){
		service.delete(uuid);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value = "/{id}:undelete")
	public ResponseEntity<Void> undelete(@PathVariable(name = "id") UUID uuid){
		service.undelete(uuid);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	private void validate(UUID uuid, Category category) {
		if(category.getId() != null && !uuid.equals(category.getId())){
			throw new BadParameterException("Path Parameter ("+uuid+") should be the same as Body Entity ("+category.getId()+")");
		}
		
	}

	private void validate(Category category) {
		if(category.getId() != null) {
			throw new BadParameterException("Should not pass ID on Category Creation");
		}
	}
	
}
