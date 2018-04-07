package br.unicamp.sindo.catalog.category;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, UUID>, JpaSpecificationExecutor<CategoryEntity> {

}
