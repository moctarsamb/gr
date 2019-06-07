package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Valeur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Valeur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValeurRepository extends JpaRepository<Valeur, Long>, JpaSpecificationExecutor<Valeur> {

}
