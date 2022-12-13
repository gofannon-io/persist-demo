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
package com.example.persistdemo.dog;


import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.List;

public class ParentEntityListener {

    public static final List<String> ON_CREATION_TRACES = new ArrayList<>();
    public static final List<String> ON_UPDATE_TRACES = new ArrayList<>();

    @PrePersist
    public void onCreation(Object rawEntity) {
        if (rawEntity instanceof ParentEntity typedEntity) {
            System.out.println("onCreation "+rawEntity);
            ON_CREATION_TRACES.add(typedEntity.toString());
        }
    }

    @PreUpdate
    public void onUpdate(Object rawEntity) {
        if (rawEntity instanceof ParentEntity typedEntity) {
            System.out.println("onUpdate "+rawEntity);
            ON_UPDATE_TRACES.add(typedEntity.toString());
        }
    }
}
