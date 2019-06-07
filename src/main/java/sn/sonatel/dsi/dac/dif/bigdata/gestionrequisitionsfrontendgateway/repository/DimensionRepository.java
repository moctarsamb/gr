package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Dimension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Dimension entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DimensionRepository extends JpaRepository<Dimension, Long>, JpaSpecificationExecutor<Dimension> {

    @Query(value = "select distinct dimension from Dimension dimension left join fetch dimension.monitors",
        countQuery = "select count(distinct dimension) from Dimension dimension")
    Page<Dimension> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct dimension from Dimension dimension left join fetch dimension.monitors")
    List<Dimension> findAllWithEagerRelationships();

    @Query("select dimension from Dimension dimension left join fetch dimension.monitors where dimension.id =:id")
    Optional<Dimension> findOneWithEagerRelationships(@Param("id") Long id);

}
