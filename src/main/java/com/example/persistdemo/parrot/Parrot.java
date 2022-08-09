package com.example.persistdemo.parrot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PARROT")
public class Parrot extends ParentEntity {


    @Column(name = "name")
    private String name;

    public Parrot() {
    }

    public Parrot(int id, String name) {
        setId(id);
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
