package br.unicamp.sindo.catalog.adm;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdmUserRepository extends PagingAndSortingRepository<AdmUserEntity, UUID>, JpaSpecificationExecutor<AdmUserEntity> {

    List<AdmUserEntity> findByEmailAndPassword(String email, String password);

    List<AdmUserEntity> findByEmail(String email);
}
