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

package com.example.persistdemo.common;

import java.util.ArrayList;
import java.util.List;

public class ApplicationLog {

    private int listenerId=-1;
    private List<String> instanceTraces = new ArrayList<>();
    private List<String> onCreationTraces = new ArrayList<>();
    private List<String> onUpdateTraces = new ArrayList<>();


    @SuppressWarnings("unused")
    public int getListenerId() {
        return listenerId;
    }

    public void setListenerId(int listenerId) {
        this.listenerId = listenerId;
    }

    @SuppressWarnings("unused")
    public List<String> getInstanceTraces() {
        return instanceTraces;
    }

    public void setInstanceTraces(List<String> instanceTraces) {
        this.instanceTraces = instanceTraces;
    }

    @SuppressWarnings("unused")
    public List<String> getOnCreationTraces() {
        return onCreationTraces;
    }

    public void setOnCreationTraces(List<String> onCreationTraces) {
        this.onCreationTraces = onCreationTraces;
    }

    @SuppressWarnings("unused")
    public List<String> getOnUpdateTraces() {
        return onUpdateTraces;
    }

    public void setOnUpdateTraces(List<String> onUpdateTraces) {
        this.onUpdateTraces = onUpdateTraces;
    }
}
