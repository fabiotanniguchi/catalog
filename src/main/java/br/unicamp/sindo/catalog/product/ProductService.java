package br.unicamp.sindo.catalog.product;

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

import static br.unicamp.sindo.catalog.product.ProductSpecification.buildSpec;

@Service
public class ProductService {

    @Autowired
    protected ProductRepository repository;

    public Product fetch(UUID id) {
        return repository.findById(id)
                .map(ProductEntity::assemble)
                .orElseThrow(() -> new NotFoundException(Product.class.getSimpleName(), id));
    }

    public List<Product> list(String name, UUID parentId, UUID categoryId, Double minPrice, Double maxPrice, String tags, String brand, Boolean highlight, Integer page, Integer pageSize) {
        Specification<ProductEntity> spec = buildSpec(Optional.ofNullable(name), Optional.of(Status.ACTIVE),
                Optional.ofNullable(parentId), Optional.ofNullable(categoryId), Optional.ofNullable(minPrice),
                Optional.ofNullable(maxPrice), Optional.ofNullable(tags), Optional.ofNullable(brand),
                Optional.ofNullable(highlight));
        return repository.findAll(spec,
                PageRequest.of(page - 1, pageSize, Sort.Direction.ASC, "createdAt"))
                .stream()
                .map(ProductEntity::assemble)
                .collect(Collectors.toList());
    }

    public Product save(Product productDTO) {
        ProductEntity entity = ProductEntity.fromDTO(productDTO);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        return repository.save(entity).assemble();
    }

    public Product update(Product productDTO) {
        if (!repository.existsById(productDTO.getId()))
            throw new NotFoundException(Product.class.getSimpleName(), productDTO.getId());
        ProductEntity entity = ProductEntity.updateUUIDDTO(fetch(productDTO.getId()), productDTO);
        entity.setUpdatedAt(new Date());
        return repository.save(entity).assemble();
    }

    public void delete(UUID uuid) {
        ProductEntity entity = ProductEntity.fromDTO(fetch(uuid));
        entity.setStatus(Status.DISABLED);
        entity.setUpdatedAt(new Date());
        repository.save(entity);
    }

    public void undelete(UUID uuid) {
        ProductEntity entity = ProductEntity.fromDTO(fetch(uuid));
        entity.setStatus(Status.ACTIVE);
        entity.setUpdatedAt(new Date());
        repository.save(entity);
    }

}
