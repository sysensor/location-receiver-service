package com.sysensor.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;

@MappedSuperclass
public class UUIDBaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "uuid")
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("created")
    private ZonedDateTime created;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    @PrePersist
    public void updateCreatedTimeStamp() {
        created = ZonedDateTime.now();
    }
}
