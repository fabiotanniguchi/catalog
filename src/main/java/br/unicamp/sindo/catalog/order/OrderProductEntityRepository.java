package br.unicamp.sindo.catalog.order;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrderProductEntityRepository extends PagingAndSortingRepository<OrderProductEntity, UUID>, JpaSpecificationExecutor<OrderProductEntity> {
}
