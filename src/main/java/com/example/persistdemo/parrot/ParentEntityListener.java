package com.example.persistdemo.parrot;


import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.List;

public class ParentEntityListener {

    public static final List<String> ON_CREATION_TRACES = new ArrayList<>();
    public static final List<String> ON_UPDATE_TRACES = new ArrayList<>();

    @PrePersist
    public void onCreation(Object rawEntity) {
        if (rawEntity instanceof Parrot typedEntity) {
            ON_CREATION_TRACES.add(toTrace(typedEntity));
        }
    }

    private static String toTrace(Parrot typedEntity) {
        return typedEntity.getId() + "|" + typedEntity.getName();
    }

    @PreUpdate
    public void onUpdate(Object rawEntity) {
        if (rawEntity instanceof Parrot typedEntity) {
            ON_UPDATE_TRACES.add(toTrace(typedEntity));
        }
    }
}
