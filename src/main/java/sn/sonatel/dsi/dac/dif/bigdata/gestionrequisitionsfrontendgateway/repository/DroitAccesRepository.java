package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.DroitAcces;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DroitAcces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroitAccesRepository extends JpaRepository<DroitAcces, Long>, JpaSpecificationExecutor<DroitAcces> {

}
