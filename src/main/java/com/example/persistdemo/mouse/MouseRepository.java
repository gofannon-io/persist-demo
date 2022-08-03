package com.example.persistdemo.mouse;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MouseRepository extends CrudRepository<Mouse,Integer> {
}
