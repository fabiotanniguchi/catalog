package br.unicamp.sindo.catalog.cart;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShoppingCartRepository extends PagingAndSortingRepository<ShoppingCartEntity, UUID>, JpaSpecificationExecutor<ShoppingCartEntity> {
}
