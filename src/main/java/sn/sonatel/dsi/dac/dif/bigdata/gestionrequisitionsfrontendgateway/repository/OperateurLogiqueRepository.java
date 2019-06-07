package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.OperateurLogique;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OperateurLogique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperateurLogiqueRepository extends JpaRepository<OperateurLogique, Long>, JpaSpecificationExecutor<OperateurLogique> {

}
