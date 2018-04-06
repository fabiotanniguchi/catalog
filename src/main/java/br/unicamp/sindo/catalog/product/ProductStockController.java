package br.unicamp.sindo.catalog.product;

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
public class ProductStockController {

	@Autowired
	ProductService service;
	
	@PutMapping(value = "/{id}:release")
	public ResponseEntity<Void> release(@RequestParam(name = "id") UUID uuid,
			@RequestBody Integer quantity){
		Product product= service.fetch(uuid);

		product.setStock(product.getStock() + quantity);
		product = service.update(uuid, product);

		return ETaggedResponseEntity.updated(product);
	}
	
	@PutMapping(value = "/{id}:reserve")
	public ResponseEntity<Void> reserve(@RequestParam(name = "id") UUID uuid,
			@RequestBody Integer quantity) {
		Product product = service.fetch(uuid);

		if (quantity > product.getStock())
			throw new BadParameterException("Reserve quantity is greater than the available stock.");
		else {
			product.setStock(product.getStock() - quantity);
			product = service.update(uuid, product);
		}

		return ETaggedResponseEntity.updated(product);
	}

}