package br.unicamp.sindo.catalog.product;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {

    List<ProductEntity> findByCategoryId(UUID categoryId);

    List<ProductEntity> findByGroupId(String groupId);

    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(p.tags) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<ProductEntity> search(@Param("searchTerm") String expr);
}
