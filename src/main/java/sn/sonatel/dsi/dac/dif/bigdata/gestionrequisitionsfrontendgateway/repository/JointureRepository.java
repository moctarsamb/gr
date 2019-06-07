package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Jointure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Jointure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JointureRepository extends JpaRepository<Jointure, Long>, JpaSpecificationExecutor<Jointure> {

}
