package com.sysensor.app.jms;

import com.sysensor.app.config.AppConfig;
import com.sysensor.app.model.LocationSignal;
import com.sysensor.app.repository.LocationSignalRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class LocationReceiverJMSListener {
    Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LocationSignalRepo locationSignalRepo;

    @JmsListener(destination = AppConfig.LOCATIONS_QUEUE_NAME, containerFactory = AppConfig.JMS_CONNECTION_FACTORY_NAME)
    public void receiveLocationMessage(LocationSignal locationSignal) {
        LOG.info(String.format("Received the signal %s", locationSignal.toString()));
        locationSignalRepo.save(locationSignal);
    }
}
