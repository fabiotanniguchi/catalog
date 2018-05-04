package br.unicamp.sindo.catalog.order;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderProductRepository extends PagingAndSortingRepository<OrderProductEntity, UUID>, JpaSpecificationExecutor<OrderProductEntity> {
}
