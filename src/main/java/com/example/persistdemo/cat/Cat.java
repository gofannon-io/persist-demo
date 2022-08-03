package com.example.persistdemo.cat;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CAT")
@EntityListeners(CatEntityListener.class)
public class Cat {

    @Id
    private int id;

    @Column(name = "name")
    private String name;

    public Cat() {
    }

    public Cat(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cat cat)) return false;
        return id == cat.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
