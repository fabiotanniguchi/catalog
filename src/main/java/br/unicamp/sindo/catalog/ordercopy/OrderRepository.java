package br.unicamp.sindo.catalog.ordercopy;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, UUID> {

	List<OrderEntity> findByUserId(String userId);
//    List<ProductEntity> findByCategoryId(UUID categoryId);
//
//    List<ProductEntity> findByGroupId(String groupId);
//
//    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
//            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
//            "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
//            "OR LOWER(p.tags) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
//    List<ProductEntity> search(@Param("searchTerm") String expr);
}
