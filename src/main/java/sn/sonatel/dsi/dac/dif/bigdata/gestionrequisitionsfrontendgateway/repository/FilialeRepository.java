package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Filiale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilialeRepository extends JpaRepository<Filiale, Long>, JpaSpecificationExecutor<Filiale> {

    @Query(value = "select distinct filiale from Filiale filiale left join fetch filiale.environnements left join fetch filiale.profils",
        countQuery = "select count(distinct filiale) from Filiale filiale")
    Page<Filiale> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct filiale from Filiale filiale left join fetch filiale.environnements left join fetch filiale.profils")
    List<Filiale> findAllWithEagerRelationships();

    @Query("select filiale from Filiale filiale left join fetch filiale.environnements left join fetch filiale.profils where filiale.id =:id")
    Optional<Filiale> findOneWithEagerRelationships(@Param("id") Long id);

}
