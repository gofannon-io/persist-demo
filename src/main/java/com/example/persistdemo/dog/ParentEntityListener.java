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
        if (rawEntity instanceof Dog typedEntity) {
            System.out.println("onCreation "+rawEntity);
            ON_CREATION_TRACES.add(toTrace(typedEntity));
        }
    }

    private static String toTrace(Dog typedEntity) {
        return typedEntity.getId() + "|" + typedEntity.getName();
    }

    @PreUpdate
    public void onUpdate(Object rawEntity) {
        if (rawEntity instanceof Dog typedEntity) {
            System.out.println("onUpdate "+rawEntity);
            ON_UPDATE_TRACES.add(toTrace(typedEntity));
        }
    }
}
