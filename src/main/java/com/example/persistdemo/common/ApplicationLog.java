package com.example.persistdemo.common;

import java.util.ArrayList;
import java.util.List;

public class ApplicationLog {

    private int listenerId=-1;
    private List<String> instanceTraces = new ArrayList<>();
    private List<String> onCreationTraces = new ArrayList<>();
    private List<String> onUpdateTraces = new ArrayList<>();


    public int getListenerId() {
        return listenerId;
    }

    public void setListenerId(int listenerId) {
        this.listenerId = listenerId;
    }

    public List<String> getInstanceTraces() {
        return instanceTraces;
    }

    public void setInstanceTraces(List<String> instanceTraces) {
        this.instanceTraces = instanceTraces;
    }

    public List<String> getOnCreationTraces() {
        return onCreationTraces;
    }

    public void setOnCreationTraces(List<String> onCreationTraces) {
        this.onCreationTraces = onCreationTraces;
    }

    public List<String> getOnUpdateTraces() {
        return onUpdateTraces;
    }

    public void setOnUpdateTraces(List<String> onUpdateTraces) {
        this.onUpdateTraces = onUpdateTraces;
    }
}
