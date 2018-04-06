package br.unicamp.sindo.catalog.product;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.unicamp.sindo.catalog.utils.repository.RootSpecification;

public class ProductSpecification extends RootSpecification<ProductEntity> {


	public static Specification<ProductEntity> buildSpec(Optional<String> name, Optional<Status> status,
														 Optional<UUID> parentId, Optional<UUID> categoryId,
														 Optional<Double> minPrice, Optional<Double> maxPrice,
														 Optional<String> tags, Optional<String> brand,
														 Optional<Boolean> highlight) {
		ProductSpecification specDefinition = new ProductSpecification(); 
		Specification<ProductEntity> specs = Specification.where(specDefinition.init());
		
		if (name.isPresent()){
			specs = specs.and(withName(name.get().toUpperCase()));
		}

		if (parentId.isPresent()) {
			specs = specs.and(withParentId(parentId.get()));
		}

		if (categoryId.isPresent()) {
			specs = specs.and(withCategoryId(categoryId.get()));
		}
		
		if (status.isPresent()){
			specs = specs.and(withStatus(status.get()));
		}

		if (minPrice.isPresent()) {
			specs = specs.and(withMinPrice(minPrice.get()));
		}

		if (maxPrice.isPresent()) {
			specs = specs.and(withMaxPrice(maxPrice.get()));
		}

		if (tags.isPresent()) {
		}

		if (brand.isPresent()) {
			specs = specs.and(withBrand(brand.get()));
		}

		if (highlight.isPresent()) {
			specs = specs.and(withHighlight(highlight.get()));
		}

		return specs;
	}

	private static Specification<ProductEntity> withHighlight(Boolean highlight) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.equal(root.get("highlight"), highlight);
			return predicate;
		};
	}

	private static Specification<ProductEntity> withBrand(String brand) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.like(root.get("brand"), brand);
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

	private static Specification<ProductEntity> withStatus(final Status status){
		return (root, criteriaQuery, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.equal(root.get("status"), status);
			return predicate;
		};
	}
	
	private static Specification<ProductEntity> withName(final String name){
	    return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get("name"), name);
            return predicate;
        };
	}

	private static Specification<ProductEntity> withParentId(final UUID parentId){
		return (root, criteriaQuery, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.equal(root.get("parentId"), parentId);
			return predicate;
		};
	}
	
}
