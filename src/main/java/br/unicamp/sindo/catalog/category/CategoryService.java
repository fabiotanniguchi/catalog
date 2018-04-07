package br.unicamp.sindo.catalog.category;

import br.unicamp.sindo.catalog.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.unicamp.sindo.catalog.category.CategorySpecification.buildSpec;

@Service
public class CategoryService {

    @Autowired
    protected CategoryRepository repository;

    public Category fetch(UUID id) {
        return repository.findById(id)
                .map(CategoryEntity::assemble)
                .orElseThrow(() -> new NotFoundException(Category.class.getSimpleName(), id));
    }

    public List<Category> list(String name, UUID parentId, Integer page, Integer pageSize) {
        Specification<CategoryEntity> spec = buildSpec(Optional.ofNullable(name), Optional.ofNullable(parentId), Optional.of(Status.ACTIVE));
        return repository.findAll(spec,
                PageRequest.of(page - 1, pageSize, Sort.Direction.ASC, "createdAt"))
                .stream()
                .map(CategoryEntity::assemble)
                .collect(Collectors.toList());
    }

    public Category save(Category categoryDTO) {
        CategoryEntity entity = CategoryEntity.fromDTO(categoryDTO);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        return repository.save(entity).assemble();
    }

    public Category update(Category categoryDTO) {
        if (!repository.existsById(categoryDTO.getId()))
            throw new NotFoundException(Category.class.getSimpleName(), categoryDTO.getId());
        CategoryEntity entity = CategoryEntity.updateUUIDDTO(fetch(categoryDTO.getId()), categoryDTO);
        entity.setUpdatedAt(new Date());
        return repository.save(entity).assemble();
    }

    public void delete(UUID uuid) {
        CategoryEntity entity = CategoryEntity.fromDTO(fetch(uuid));
        entity.setStatus(Status.DISABLED);
        entity.setUpdatedAt(new Date());
        repository.save(entity);
    }

    public void undelete(UUID uuid) {
        CategoryEntity entity = CategoryEntity.fromDTO(fetch(uuid));
        entity.setStatus(Status.ACTIVE);
        entity.setUpdatedAt(new Date());
        repository.save(entity);
    }

}
