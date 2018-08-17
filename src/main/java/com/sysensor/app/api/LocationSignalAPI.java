package com.sysensor.app.api;

import com.sysensor.app.common.APIUtility;
import com.sysensor.app.config.APIConfig;
import com.sysensor.app.model.LocationSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIConfig.MAIN_PATH_API)
@CrossOrigin(origins = {APIConfig.PATH_CONFIG_CROSS_ORIGIN_URL})
public class LocationSignalAPI {

    Logger LOG = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = APIConfig.SUB_PATH_SIGNAL, method = RequestMethod.GET, produces = APIUtility.APPLICATION_JSON)
    public LocationSignal getLocationSignal(@RequestHeader HttpHeaders headers) {
        APIUtility.printHeaders(headers, LOG);
        LocationSignal locationSignal = new LocationSignal();
        locationSignal.setLat("41.619549");
        locationSignal.setLng("-93.598022");

        LOG.info("Returning the Latest LocationSignal" + locationSignal.toString());
        return locationSignal;
    }

    @RequestMapping(value = APIConfig.SUB_PATH_SIGNAL, method = RequestMethod.POST, consumes = APIUtility.APPLICATION_JSON, produces = APIUtility.APPLICATION_JSON)
    @PreAuthorize("hasRole('ADMIN')")
    public LocationSignal postLocationSignal(@RequestHeader HttpHeaders headers, @RequestBody LocationSignal locationSignal) {
        APIUtility.printHeaders(headers, LOG);
        LOG.info(locationSignal.toString());
        return locationSignal;
    }
}
