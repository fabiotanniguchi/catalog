package br.unicamp.sindo.catalog.category;

import br.unicamp.sindo.catalog.Status;
import br.unicamp.sindo.catalog.utils.repository.RootSpecification;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Optional;
import java.util.UUID;

public class CategorySpecification extends RootSpecification<CategoryEntity> {

    public static Specification<CategoryEntity> buildSpec(Optional<String> name, Optional<UUID> parentId, Optional<String> groupId, Optional<Status> status) {
        CategorySpecification specDefinition = new CategorySpecification();
        Specification<CategoryEntity> specs = Specification.where(specDefinition.init());

        if (name.isPresent()) {
            specs = specs.and(withName(name.get().toUpperCase()));
        }

        if (parentId.isPresent()) {
            specs = specs.and(withParentId(parentId.get()));
        }

        if (status.isPresent()) {
            specs = specs.and(withStatus(status.get()));
        }
        
        if (groupId.isPresent()) {
        	specs = specs.and(withGroupId(groupId.get()));
        }

        return specs;
    }

    private static Specification<CategoryEntity> withStatus(final Status status) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("status"), status);
            return predicate;
        };
    }

    private static Specification<CategoryEntity> withName(final String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get("name"), name);
            return predicate;
        };
    }
    
    private static Specification<CategoryEntity> withGroupId(final String groupId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get("groupId"), groupId);
            return predicate;
        };
    }

    private static Specification<CategoryEntity> withParentId(final UUID parentId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("parentId"), parentId);
            return predicate;
        };
    }
}
