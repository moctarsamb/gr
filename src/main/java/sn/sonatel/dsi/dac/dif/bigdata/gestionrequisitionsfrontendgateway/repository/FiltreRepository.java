package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filtre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Filtre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FiltreRepository extends JpaRepository<Filtre, Long>, JpaSpecificationExecutor<Filtre> {

    @Query(value = "select distinct filtre from Filtre filtre left join fetch filtre.clauses",
        countQuery = "select count(distinct filtre) from Filtre filtre")
    Page<Filtre> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct filtre from Filtre filtre left join fetch filtre.clauses")
    List<Filtre> findAllWithEagerRelationships();

    @Query("select filtre from Filtre filtre left join fetch filtre.clauses where filtre.id =:id")
    Optional<Filtre> findOneWithEagerRelationships(@Param("id") Long id);

}
