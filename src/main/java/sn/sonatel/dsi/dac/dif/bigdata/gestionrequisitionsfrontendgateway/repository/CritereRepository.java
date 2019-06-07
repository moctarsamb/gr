package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Critere;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Critere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CritereRepository extends JpaRepository<Critere, Long>, JpaSpecificationExecutor<Critere> {

}
