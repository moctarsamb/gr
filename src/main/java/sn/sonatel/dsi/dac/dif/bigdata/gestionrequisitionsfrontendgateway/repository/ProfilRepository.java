package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Profil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Profil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long>, JpaSpecificationExecutor<Profil> {

    @Query(value = "select distinct profil from Profil profil left join fetch profil.colonnes left join fetch profil.typeExtractions",
        countQuery = "select count(distinct profil) from Profil profil")
    Page<Profil> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct profil from Profil profil left join fetch profil.colonnes left join fetch profil.typeExtractions")
    List<Profil> findAllWithEagerRelationships();

    @Query("select profil from Profil profil left join fetch profil.colonnes left join fetch profil.typeExtractions where profil.id =:id")
    Optional<Profil> findOneWithEagerRelationships(@Param("id") Long id);

}
