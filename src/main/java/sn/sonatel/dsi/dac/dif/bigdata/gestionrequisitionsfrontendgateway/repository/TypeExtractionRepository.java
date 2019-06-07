package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TypeExtraction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeExtractionRepository extends JpaRepository<TypeExtraction, Long>, JpaSpecificationExecutor<TypeExtraction> {

    @Query(value = "select distinct type_extraction from TypeExtraction type_extraction left join fetch type_extraction.monitors",
        countQuery = "select count(distinct type_extraction) from TypeExtraction type_extraction")
    Page<TypeExtraction> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct type_extraction from TypeExtraction type_extraction left join fetch type_extraction.monitors")
    List<TypeExtraction> findAllWithEagerRelationships();

    @Query("select type_extraction from TypeExtraction type_extraction left join fetch type_extraction.monitors where type_extraction.id =:id")
    Optional<TypeExtraction> findOneWithEagerRelationships(@Param("id") Long id);

}
