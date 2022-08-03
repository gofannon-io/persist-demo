package com.example.persistdemo.dog;

import com.example.persistdemo.cat.CatEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DOG")
public class Dog extends ParentEntity {


    @Column(name = "name")
    private String name;

    public Dog() {
    }

    public Dog(int id, String name) {
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
