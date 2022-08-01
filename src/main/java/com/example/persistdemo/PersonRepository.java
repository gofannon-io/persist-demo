package com.example.persistdemo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityListeners;

@Repository
@EntityListeners(AbstractEntityListener.class)
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
