package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Flux;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Flux entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FluxRepository extends JpaRepository<Flux, Long>, JpaSpecificationExecutor<Flux> {

}
