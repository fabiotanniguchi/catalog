package br.unicamp.sindo.catalog.category;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, UUID>, JpaSpecificationExecutor<CategoryEntity> {

}
