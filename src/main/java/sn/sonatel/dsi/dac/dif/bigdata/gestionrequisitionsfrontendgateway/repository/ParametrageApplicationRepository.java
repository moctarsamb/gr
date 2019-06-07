package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ParametrageApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParametrageApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametrageApplicationRepository extends JpaRepository<ParametrageApplication, Long>, JpaSpecificationExecutor<ParametrageApplication> {

}
