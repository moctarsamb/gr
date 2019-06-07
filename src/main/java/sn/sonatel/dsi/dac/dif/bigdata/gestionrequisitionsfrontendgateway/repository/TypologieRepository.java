package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Typologie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Typologie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypologieRepository extends JpaRepository<Typologie, Long>, JpaSpecificationExecutor<Typologie> {

}
