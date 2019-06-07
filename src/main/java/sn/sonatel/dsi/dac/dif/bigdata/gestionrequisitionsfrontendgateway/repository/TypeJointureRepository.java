package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeJointure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeJointure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeJointureRepository extends JpaRepository<TypeJointure, Long>, JpaSpecificationExecutor<TypeJointure> {

}
