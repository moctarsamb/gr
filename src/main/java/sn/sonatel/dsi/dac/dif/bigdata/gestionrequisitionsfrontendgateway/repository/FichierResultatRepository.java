package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.FichierResultat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FichierResultat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FichierResultatRepository extends JpaRepository<FichierResultat, Long>, JpaSpecificationExecutor<FichierResultat> {

}
