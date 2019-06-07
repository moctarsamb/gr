package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Provenance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Provenance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProvenanceRepository extends JpaRepository<Provenance, Long>, JpaSpecificationExecutor<Provenance> {

}
