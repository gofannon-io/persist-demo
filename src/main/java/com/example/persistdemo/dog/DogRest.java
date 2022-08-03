package com.example.persistdemo.dog;

import com.example.persistdemo.common.ErrorResponse;
import com.example.persistdemo.common.PetUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("dogs")
public class DogRest {

    private DogRepository repository;

    @Autowired
    public void setRepository(DogRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = "application/json")
    public List<Dog> getAll() {
        List<Dog> allDogs = new ArrayList<>();
        repository.findAll().forEach(allDogs::add);
        return allDogs;
    }

    @PostMapping( consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Dog inCreationDog) {
        if (inCreationDog.getId() <= 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The Dog id '" + inCreationDog.getId() + "' is invalid"));
        }

        Optional<Dog> existingDogOption = repository.findById(inCreationDog.getId());
        if (existingDogOption.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("A dog with id '" + inCreationDog.getId() + "' already exists"));
        }

        Dog createdDog = repository.save(inCreationDog);
        return ResponseEntity.ok(createdDog);
    }

    @PutMapping(value = "{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody PetUpdate dogUpdate) {
        int idAsInt;
        try {
            idAsInt = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Dog's id '" + id + "' is not valid"));
        }

        Optional<Dog> optionalDog = repository.findById(idAsInt);
        if (optionalDog.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Dog with id " + id + " is not found"));
        }

        Dog dog = optionalDog.get();
        dog.setName(dogUpdate.getName());
        repository.save(dog);
        return ResponseEntity.ok(dog);
    }

    @GetMapping(value = "log/creation", produces = "application/json")
    public List<String> getLogCreationTraces() {
        return ParentEntityListener.ON_CREATION_TRACES;
    }

    @GetMapping(value = "log/update", produces = "application/json")
    public List<String> getLogUpdateTraces() {
        return ParentEntityListener.ON_UPDATE_TRACES;
    }

}