package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.EnvoiResultat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EnvoiResultat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvoiResultatRepository extends JpaRepository<EnvoiResultat, Long>, JpaSpecificationExecutor<EnvoiResultat> {

}
