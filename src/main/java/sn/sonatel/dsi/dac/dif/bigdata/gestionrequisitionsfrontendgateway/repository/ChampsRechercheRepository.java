package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChampsRecherche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChampsRechercheRepository extends JpaRepository<ChampsRecherche, Long>, JpaSpecificationExecutor<ChampsRecherche> {

}
