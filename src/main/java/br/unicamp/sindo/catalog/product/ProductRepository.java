package br.unicamp.sindo.catalog.product;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {

    List<ProductEntity> findByCategoryId(UUID categoryId);

    List<ProductEntity> findByGroupId(String groupId);
}
