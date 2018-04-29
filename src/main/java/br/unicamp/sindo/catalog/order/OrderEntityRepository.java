package br.unicamp.sindo.catalog.order;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrderEntityRepository extends PagingAndSortingRepository<OrderEntity, UUID>, JpaSpecificationExecutor<OrderEntity> {
}
