package com.example.persistdemo.parrot;

import com.example.persistdemo.common.ApplicationLog;
import com.example.persistdemo.common.ErrorResponse;
import com.example.persistdemo.common.PetUpdate;
import com.example.persistdemo.mouse.ParentEntityListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("parrots")
public class ParrotRest {

    private ParrotRepository repository;

    @Autowired
    public void setRepository(ParrotRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = "application/json")
    public List<Parrot> getAll() {
        List<Parrot> allParrots = new ArrayList<>();
        repository.findAll().forEach(allParrots::add);
        return allParrots;
    }

    @PostMapping( consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Parrot inCreationParrot) {
        if (inCreationParrot.getId() <= 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The Dog id '" + inCreationParrot.getId() + "' is invalid"));
        }

        Optional<Parrot> existingDogOption = repository.findById(inCreationParrot.getId());
        if (existingDogOption.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("A dog with id '" + inCreationParrot.getId() + "' already exists"));
        }

        Parrot createdParrot = repository.save(inCreationParrot);
        return ResponseEntity.ok(createdParrot);
    }

    @PutMapping(value = "{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody PetUpdate dogUpdate) {
        int idAsInt;
        try {
            idAsInt = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Dog's id '" + id + "' is not valid"));
        }

        Optional<Parrot> optionalDog = repository.findById(idAsInt);
        if (optionalDog.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Dog with id " + id + " is not found"));
        }

        Parrot parrot = optionalDog.get();
        parrot.setName(dogUpdate.getName());
        repository.save(parrot);
        return ResponseEntity.ok(parrot);
    }


    @GetMapping(value = "logs", produces = "application/json")
    public ApplicationLog getLogs() {
        ApplicationLog applicationLog = new ApplicationLog();
        applicationLog.setInstanceTraces(com.example.persistdemo.mouse.ParentEntityListener.INSTANCE_CREATION_TRACES);
        applicationLog.setOnCreationTraces(com.example.persistdemo.mouse.ParentEntityListener.ON_CREATION_TRACES);
        applicationLog.setOnUpdateTraces(ParentEntityListener.ON_UPDATE_TRACES);
        return applicationLog;
    }
}