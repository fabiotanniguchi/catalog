package br.unicamp.sindo.catalog.utils.repository;

import org.springframework.data.jpa.domain.Specification;

public class RootSpecification<T> {

	public Specification<T> init() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
    }
}
