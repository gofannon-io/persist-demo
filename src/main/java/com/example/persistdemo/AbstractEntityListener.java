package com.example.persistdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class AbstractEntityListener {

    //private Clock clock = Clock.systemUTC();

    private ClockProvider clockProvider;
    static AtomicInteger creationId = new AtomicInteger();

    @Autowired
    public AbstractEntityListener() {
        System.out.println("AbstractEntityListener " + creationId.getAndIncrement());
    }

    @Autowired
    public void setClockProvider(ClockProvider clockProvider) {
        this.clockProvider = clockProvider;
    }

    @PrePersist
    public void onCreation(Object entity) {
        Date now = new Date(clockProvider.getClock().millis());

        if (entity instanceof AbstractEntity entity1) {
            if (entity1.getCreationDate() == null) {
                entity1.setCreationDate(now);
            }
            entity1.setUpdateDate(now);
        }
    }


    @PreUpdate
    public void onUpdate(Object entity) {
        Date now = new Date(clockProvider.getClock().millis());
        if (entity instanceof AbstractEntity) {
            ((AbstractEntity) entity).setUpdateDate(now);
        }
    }

}
