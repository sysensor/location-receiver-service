package com.sysensor.app.repository;

import com.sysensor.app.config.APIConfig;
import com.sysensor.app.model.LocationSignal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {APIConfig.PATH_CONFIG_CROSS_ORIGIN_URL})
@RepositoryRestResource(collectionResourceRel = APIConfig.SUB_PATH_SIGNAL, path = APIConfig.SUB_PATH_SIGNAL)
@Transactional
public interface LocationSignalRepo extends JpaRepository<LocationSignal, String> {
    @Query("select l from LocationSignal l where l.bus_uuid=:" + APIConfig.PARAM_SIGNAL_BUS_UUID)
    public Page<LocationSignal> findAllByBusUUID(@Param(APIConfig.PARAM_SIGNAL_BUS_UUID) String busUUID, Pageable pageable);
}
