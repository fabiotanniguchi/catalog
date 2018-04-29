package br.unicamp.sindo.catalog.adm;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface AdmUserEntityRepository extends PagingAndSortingRepository<AdmUserEntity, UUID>, JpaSpecificationExecutor<AdmUserEntity> {
}
