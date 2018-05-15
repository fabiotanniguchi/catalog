package br.unicamp.sindo.catalog.product;

import br.unicamp.sindo.catalog.error.BadParameterException;
import br.unicamp.sindo.catalog.utils.web.ETaggedResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/reservation")
public class ProductStockController {

    @Autowired
    protected ProductService service;

    @PutMapping(value = "/release/{id}")
    public ResponseEntity<Void> release(@PathVariable(name = "id") UUID uuid,
                                        @RequestBody Integer quantity) {
        Product product = service.fetch(uuid);

        product.setStock(product.getStock() + quantity);
        product = service.update(product);

        return ETaggedResponseEntity.updated(product);
    }

    @PutMapping(value = "/reserve/{id}")
    public ResponseEntity<Void> reserve(@PathVariable(name = "id") UUID uuid,
                                        @RequestBody Integer quantity) {
        Product product = service.fetch(uuid);

        if (quantity > product.getStock())
            throw new BadParameterException("Reserve quantity is greater than the available stock.");
        else {
            product.setStock(product.getStock() - quantity);
            product = service.update(product);
        }

        return ETaggedResponseEntity.updated(product);
    }

}
