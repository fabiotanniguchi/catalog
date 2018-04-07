package br.unicamp.sindo.catalog.product;

import br.unicamp.sindo.catalog.error.BadParameterException;
import br.unicamp.sindo.catalog.utils.web.ETaggedResponseEntity;
import br.unicamp.sindo.catalog.utils.web.PageableResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    protected ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> list(@RequestParam(required = false) String name
            , @RequestParam(name = "parent_product", required = false) UUID parentId
            , @RequestParam(name = "category_id", required = false) UUID categoryId
            , @RequestParam(name = "min_price", required = false) Double minPrice
            , @RequestParam(name = "max_price", required = false) Double maxPrice
            , @RequestParam(name = "tags", required = false) String tags
            , @RequestParam(name = "brand", required = false) String brand
            , @RequestParam(name = "highlight", required = false) Boolean highlight
            , @RequestParam(defaultValue = "1") Integer page) {
        int pageSize = 50;
        return PageableResponseEntity.ok(service.list(name, parentId, categoryId, minPrice,
                maxPrice, tags, brand, highlight, page, pageSize + 1), page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> get(@PathVariable(name = "id") UUID uuid,
                                       @RequestHeader(name = "ETag", required = false) String etag) {
        Product product = service.fetch(uuid);
        return ETaggedResponseEntity.ok(product, etag);
    }

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody Product product) {
        validate(product);
        product = service.save(product);
        return ETaggedResponseEntity.created(product);
    }

    @PostMapping(value = "/{id}:release")
    public ResponseEntity<Void> post(@RequestParam(name = "id") UUID uuid,
                                     @RequestBody int amount) {
        Product product = service.fetch(uuid);
        product.setStock(product.getStock() + amount);
        return ETaggedResponseEntity.updated(product);
    }

    @PutMapping
    public ResponseEntity<Void> put(@RequestBody Product product) {
        product = service.update(product);
        return ETaggedResponseEntity.updated(product);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID uuid) {
        service.delete(uuid);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{id}:undelete")
    public ResponseEntity<Void> undelete(@PathVariable(name = "id") UUID uuid) {
        service.undelete(uuid);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    private void validate(UUID uuid, Product product) {
        if (product.getId() != null && !uuid.equals(product.getId())) {
            throw new BadParameterException("Path Parameter (" + uuid + ") should be the same as Body Entity (" + product.getId() + ")");
        }

    }

    private void validate(Product product) {
        if (product.getId() != null) {
            throw new BadParameterException("Should not pass ID on Product Creation");
        }
        if (product.getStock() == null) {
            product.setStatus(Status.ACTIVE);
        }
    }

}
