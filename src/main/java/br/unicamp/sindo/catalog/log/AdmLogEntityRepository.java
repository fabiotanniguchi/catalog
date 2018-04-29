package br.unicamp.sindo.catalog.log;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface AdmLogEntityRepository extends PagingAndSortingRepository<AdmLogEntity, UUID>, JpaSpecificationExecutor<AdmLogEntity> {
}
