/*
 * Copyright (c) 2022 gofannon.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.persistdemo.mouse;


import com.example.persistdemo.ClockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ParentEntityListener {

    public static final List<String> ON_CREATION_TRACES = new ArrayList<>();
    public static final List<String> ON_UPDATE_TRACES = new ArrayList<>();

    public static final AtomicInteger INSTANCE_CREATION_COUNTER = new AtomicInteger();
    public static final List<String> INSTANCE_CREATION_TRACES = new ArrayList<>();

    private final int instanceId;
    private Clock clock;

    public ParentEntityListener() {
        instanceId = INSTANCE_CREATION_COUNTER.incrementAndGet();
        INSTANCE_CREATION_TRACES.add("Create instance number " + instanceId);
    }

    @Autowired
    public void setClockProvider(ClockProvider clockProvider) {
        this.clock = clockProvider.getClock();
        INSTANCE_CREATION_TRACES.add("Inject clock provider to instance number " + instanceId);
    }


    @PrePersist
    public void onCreation(Object rawEntity) {
        if (rawEntity instanceof Mouse typedEntity) {
            System.out.println("onCreation " + rawEntity);
            ON_CREATION_TRACES.add("Create mouse " + typedEntity.getName() + " (id=" + typedEntity.getId() + ") from listener " + instanceId);
            Date now = now();
            typedEntity.setCreationDate(now);
            typedEntity.setUpdateDate(now);
        }
    }

    private Date now() {
        return new Date(clock.millis());
    }

    
    @PreUpdate
    public void onUpdate(Object rawEntity) {
        if (rawEntity instanceof Mouse typedEntity) {
            System.out.println("onUpdate " + rawEntity);
            ON_UPDATE_TRACES.add("Update mouse " + typedEntity.getName() + " (id=" + typedEntity.getId() + ") from listener " + instanceId);
            Date now = now();
            typedEntity.setUpdateDate(now);
        }
    }

    public int getInstanceId() {
        return instanceId;
    }
}
