package com.example.persistdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Clock;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class AbstractEntityListener {

    public static final AtomicInteger INSTANCE_COUNT = new AtomicInteger();
    public static final AtomicInteger ON_CREATION_COUNT = new AtomicInteger();
    public static final AtomicInteger ON_UPDATE_COUNT = new AtomicInteger();

    private Clock clock;

    private int instanceId;

    public AbstractEntityListener() {
        instanceId = INSTANCE_COUNT.incrementAndGet();
        System.out.println("AbstractEntityListener::create instance "+instanceId);
    }

    @Autowired
    public void setClockProvider(ClockProvider clockProvider) {
        this.clock = clockProvider.getClock();
    }


//    @PrePersist
//    @PreUpdate
//    public void onCreationOrUpdate(Object rawEntity) {
//        if (rawEntity instanceof AbstractEntity typedEntity) {
//            Date now = now();
//            ON_CREATION_COUNT.incrementAndGet();
//            if (typedEntity.getCreationDate() == null) {
//                typedEntity.setCreationDate(now);
//            }
//            typedEntity.setUpdateDate(now);
//        }
//    }

    @PrePersist
    public void onCreation(Object rawEntity) {
        if (rawEntity instanceof AbstractEntity typedEntity) {
            Date now = now();
            ON_CREATION_COUNT.incrementAndGet();
                typedEntity.setCreationDate(now);
            typedEntity.setUpdateDate(now);
        }
    }

    private Date now() {
        return new Date(clock.millis());
    }


    @PreUpdate
    public void onUpdate(Object entity) {
        if (entity instanceof AbstractEntity typedEntity) {
            Date now = now();
            ON_UPDATE_COUNT.incrementAndGet();
            if( typedEntity.getCreationDate()==null) {
                typedEntity.setCreationDate(now);
            }
            typedEntity.setUpdateDate(now);
        }
    }

}