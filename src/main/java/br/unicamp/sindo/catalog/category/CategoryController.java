package br.unicamp.sindo.catalog.category;

import br.unicamp.sindo.catalog.Status;
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
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    protected CategoryService service;

    @GetMapping
    public ResponseEntity<List<Category>> list(@RequestParam(required = false) String name
            , @RequestParam(name = "parent_category", required = false) UUID parentId
            , @RequestParam(name = "group_id", required = false) String groupId
            , @RequestParam(defaultValue = "1") Integer page) {
        int pageSize = 50;
        return PageableResponseEntity.ok(service.list(name, parentId, groupId, page, pageSize + 1), page, pageSize + 1);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> get(@PathVariable(name = "id") UUID uuid,
                                        @RequestHeader(name = "ETag", required = false) String etag) {
        Category category = service.fetch(uuid);
        return ETaggedResponseEntity.ok(category, etag);
    }

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody Category category) {
        validate(category);
        category = service.save(category);
        return ETaggedResponseEntity.created(category);
    }

    @PutMapping
    public ResponseEntity<Void> put(@RequestBody Category category) {
        category = service.update(category);
        return ETaggedResponseEntity.updated(category);
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

    @GetMapping(value = "/group/{id}")
    public List<Category> getByGroup(@PathVariable(name = "id") String groupId) {
        return service.fetchByGroup(groupId);
    }

    private void validate(UUID uuid, Category category) {
        if (category.getId() != null && !uuid.equals(category.getId())) {
            throw new BadParameterException("Path Parameter (" + uuid + ") should be the same as Body Entity (" + category.getId() + ")");
        }

    }

    private void validate(Category category) {
        if (category.getId() != null) {
            throw new BadParameterException("Should not pass ID on Category Creation");
        }
        if (category.getStatus() == null) {
            category.setStatus(Status.ACTIVE);
        }
    }

}
