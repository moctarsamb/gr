package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Operande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperandeRepository extends JpaRepository<Operande, Long>, JpaSpecificationExecutor<Operande> {

    @Query(value = "select distinct operande from Operande operande left join fetch operande.colonnes left join fetch operande.valeurs",
        countQuery = "select count(distinct operande) from Operande operande")
    Page<Operande> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct operande from Operande operande left join fetch operande.colonnes left join fetch operande.valeurs")
    List<Operande> findAllWithEagerRelationships();

    @Query("select operande from Operande operande left join fetch operande.colonnes left join fetch operande.valeurs where operande.id =:id")
    Optional<Operande> findOneWithEagerRelationships(@Param("id") Long id);

}
