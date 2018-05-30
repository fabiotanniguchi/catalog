package br.unicamp.sindo.catalog.product;

import br.unicamp.sindo.catalog.Status;
import br.unicamp.sindo.catalog.utils.repository.RootSpecification;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductSpecification extends RootSpecification<ProductEntity> {


    public static Specification<ProductEntity> buildSpec(Optional<String> name, Optional<Status> status,
                                                         Optional<UUID> parentId, Optional<List<UUID>> categoryIds,
                                                         Optional<Double> minPrice, Optional<Double> maxPrice, Optional<List<String>> brands,
                                                         Optional<Boolean> highlight, Optional<List<String>> groupIds) {
        ProductSpecification specDefinition = new ProductSpecification();
        Specification<ProductEntity> specs = Specification.where(specDefinition.init());

        if (name.isPresent()) {
            specs = specs.and(withName(name.get().toUpperCase()));
        }

        if (parentId.isPresent()) {
            specs = specs.and(withParentId(parentId.get()));
        }

        if (categoryIds.isPresent()  && !categoryIds.get().isEmpty()) {
        	Specification<ProductEntity> orCategories = withCategoryId(categoryIds.get().get(0));
        	for(int i = 1; i < categoryIds.get().size(); i++){
        		orCategories = orCategories.or(withCategoryId(categoryIds.get().get(i)));
        	}
            specs = specs.and(orCategories);
        }

        if (status.isPresent()) {
            specs = specs.and(withStatus(status.get()));
        }

        if (minPrice.isPresent()) {
            specs = specs.and(withMinPrice(minPrice.get()));
        }

        if (maxPrice.isPresent()) {
            specs = specs.and(withMaxPrice(maxPrice.get()));
        }
        if (brands.isPresent() && !brands.get().isEmpty()) {
        	Specification<ProductEntity> orBrands = withBrand(brands.get().get(0));
        	for(int i = 1; i < brands.get().size(); i++){
        		orBrands = orBrands.or(withBrand(brands.get().get(i)));
        	}
            specs = specs.and(orBrands);
        }

        if (highlight.isPresent()) {
            specs = specs.and(withHighlight(highlight.get()));
        }
        
        if (groupIds.isPresent() && !groupIds.get().isEmpty()) {
        	Specification<ProductEntity> orGroups = withGroup(groupIds.get().get(0));
        	for(int i = 1; i < groupIds.get().size(); i++){
        		orGroups = orGroups.or(withGroup(groupIds.get().get(i)));
        	}
            specs = specs.and(orGroups);
        }

        return specs;
    }

    private static Specification<ProductEntity> withGroup(String group) {
    	return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get("groupId"), "%" + group + "%");
            return predicate;
        };
	}

	private static Specification<ProductEntity> withHighlight(Boolean highlight) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("highlight"), highlight);
            return predicate;
        };
    }

    private static Specification<ProductEntity> withBrand(String brand) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get("brand"), "%" + brand + "%");
            return predicate;
        };
    }

    private static Specification<ProductEntity> withMinPrice(final Double minPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            return predicate;
        };
    }

    private static Specification<ProductEntity> withMaxPrice(final Double maxPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            return predicate;
        };
    }

    private static Specification<ProductEntity> withCategoryId(final UUID categoryId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("categoryId"), categoryId);
            return predicate;
        };
    }

    private static Specification<ProductEntity> withStatus(final Status status) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("status"), status);
            return predicate;
        };
    }

    private static Specification<ProductEntity> withName(final String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
            return predicate;
        };
    }

    private static Specification<ProductEntity> withParentId(final UUID parentId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("parentId"), parentId);
            return predicate;
        };
    }

}
