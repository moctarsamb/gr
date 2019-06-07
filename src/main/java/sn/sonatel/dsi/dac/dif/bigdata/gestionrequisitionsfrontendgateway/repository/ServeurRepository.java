package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Serveur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Serveur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServeurRepository extends JpaRepository<Serveur, Long>, JpaSpecificationExecutor<Serveur> {

}
