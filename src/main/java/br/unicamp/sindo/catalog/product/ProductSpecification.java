package br.unicamp.sindo.catalog.product;

import br.unicamp.sindo.catalog.utils.repository.RootSpecification;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

public class ProductSpecification extends RootSpecification<ProductEntity> {

    public static Specification<ProductEntity> buildSpec(Optional<String> name, Optional<Status> status) {
        ProductSpecification specDefinition = new ProductSpecification();
        Specification<ProductEntity> specs = Specification.where(specDefinition.init());

        if (name.isPresent()) {
            specs = specs.and(withName(name.get().toUpperCase()));
        }

        if (status.isPresent()) {
            specs = specs.and(withStatus(status.get()));
        }

        return specs;
    }

    private static Specification<ProductEntity> withStatus(final Status status) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("status"), status);
            return predicate;
        };
    }

    private static Specification<ProductEntity> withName(final String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get("name"), name);
            return predicate;
        };
    }

}
