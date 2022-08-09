package com.example.persistdemo.parrot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParrotRepository extends CrudRepository<Parrot,Integer> {
}
