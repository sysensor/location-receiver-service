package com.sysensor.app.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class LocationSignal extends UUIDBaseEntity {
    public String lat;
    public String lng;
    @NotNull
    private String bus_uuid;

    public String getBus_uuid() {
        return bus_uuid;
    }

    public void setBus_uuid(String bus_uuid) {
        this.bus_uuid = bus_uuid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "LocationSignal{" +
                "bus_uuid='" + bus_uuid + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
