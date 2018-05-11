package br.unicamp.sindo.catalog.cart;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShoppingCartProductRepository extends PagingAndSortingRepository<ShoppingCartProductEntity, UUID>, JpaSpecificationExecutor<ShoppingCartProductEntity> {

    List<ShoppingCartProductEntity> findByUserId(UUID userId);

    List<ShoppingCartProductEntity> findByUserIdAndProductId(UUID userId, UUID productId);
}
