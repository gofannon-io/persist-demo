package com.example.persistdemo.mouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MOUSE")
public class Mouse extends ParentEntity {


    @Column(name = "name")
    private String name;

    public Mouse() {
    }

    public Mouse(int id, String name) {
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
