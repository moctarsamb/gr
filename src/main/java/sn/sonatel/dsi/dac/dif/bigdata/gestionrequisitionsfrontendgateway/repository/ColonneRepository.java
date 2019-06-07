package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Colonne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColonneRepository extends JpaRepository<Colonne, Long>, JpaSpecificationExecutor<Colonne> {

    @Query(value = "select distinct colonne from Colonne colonne left join fetch colonne.typeExtractionRequetees",
        countQuery = "select count(distinct colonne) from Colonne colonne")
    Page<Colonne> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct colonne from Colonne colonne left join fetch colonne.typeExtractionRequetees")
    List<Colonne> findAllWithEagerRelationships();

    @Query("select colonne from Colonne colonne left join fetch colonne.typeExtractionRequetees where colonne.id =:id")
    Optional<Colonne> findOneWithEagerRelationships(@Param("id") Long id);

}
