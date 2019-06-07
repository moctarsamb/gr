package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Environnement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Environnement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvironnementRepository extends JpaRepository<Environnement, Long>, JpaSpecificationExecutor<Environnement> {

}
