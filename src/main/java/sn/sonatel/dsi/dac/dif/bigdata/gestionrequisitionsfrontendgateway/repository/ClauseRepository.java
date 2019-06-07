package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Clause;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Clause entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClauseRepository extends JpaRepository<Clause, Long>, JpaSpecificationExecutor<Clause> {

    @Query(value = "select distinct clause from Clause clause left join fetch clause.operandes",
        countQuery = "select count(distinct clause) from Clause clause")
    Page<Clause> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct clause from Clause clause left join fetch clause.operandes")
    List<Clause> findAllWithEagerRelationships();

    @Query("select clause from Clause clause left join fetch clause.operandes where clause.id =:id")
    Optional<Clause> findOneWithEagerRelationships(@Param("id") Long id);

}
