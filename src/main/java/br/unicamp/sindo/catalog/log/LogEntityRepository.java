package br.unicamp.sindo.catalog.log;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface LogEntityRepository extends PagingAndSortingRepository<LogEntity, UUID>, JpaSpecificationExecutor<LogEntity> {
}
