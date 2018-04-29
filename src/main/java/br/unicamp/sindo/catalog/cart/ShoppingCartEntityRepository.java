package br.unicamp.sindo.catalog.cart;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ShoppingCartEntityRepository extends PagingAndSortingRepository<ShoppingCartEntity, UUID>, JpaSpecificationExecutor<ShoppingCartEntity> {
}
