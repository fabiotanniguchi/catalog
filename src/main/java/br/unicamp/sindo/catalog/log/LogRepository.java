package br.unicamp.sindo.catalog.log;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogRepository extends PagingAndSortingRepository<LogEntity, UUID>, JpaSpecificationExecutor<LogEntity> {
}
