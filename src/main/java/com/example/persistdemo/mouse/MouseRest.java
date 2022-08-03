package com.example.persistdemo.mouse;

import com.example.persistdemo.common.ApplicationLog;
import com.example.persistdemo.common.ErrorResponse;
import com.example.persistdemo.common.PetUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("mice")
public class MouseRest {

    private MouseRepository repository;

    private ParentEntityListener listener;

    @Autowired
    public void setListener(ParentEntityListener listener) {
        this.listener = listener;
    }

    @Autowired
    public void setRepository(MouseRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = "application/json")
    public List<Mouse> getAll() {
        List<Mouse> allMouses = new ArrayList<>();
        repository.findAll().forEach(allMouses::add);
        return allMouses;
    }

    @PostMapping( consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody MouseCreation inCreationMouse) {
        if (inCreationMouse.getId() <= 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The Mouse id '" + inCreationMouse.getId() + "' is invalid"));
        }

        Optional<Mouse> existingMouseOption = repository.findById(inCreationMouse.getId());
        if (existingMouseOption.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("A mouse with id '" + inCreationMouse.getId() + "' already exists"));
        }

        Mouse createdMouse = repository.save(new Mouse(inCreationMouse.getId(), inCreationMouse.getName()));
        return ResponseEntity.ok(createdMouse);
    }

    @PutMapping(value = "{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody PetUpdate mouseUpdate) {
        int idAsInt;
        try {
            idAsInt = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Mouse's id '" + id + "' is not valid"));
        }

        Optional<Mouse> optionalMouse = repository.findById(idAsInt);
        if (optionalMouse.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Mouse with id " + id + " is not found"));
        }

        Mouse mouse = optionalMouse.get();
        mouse.setName(mouseUpdate.getName());
        repository.save(mouse);
        return ResponseEntity.ok(mouse);
    }

    @GetMapping(value = "logs", produces = "application/json")
    public ApplicationLog getLogs() {
        ApplicationLog applicationLog = new ApplicationLog();
        applicationLog.setInstanceTraces(ParentEntityListener.INSTANCE_CREATION_TRACES);
        applicationLog.setOnCreationTraces(ParentEntityListener.ON_CREATION_TRACES);
        applicationLog.setOnUpdateTraces(ParentEntityListener.ON_UPDATE_TRACES);
        applicationLog.setListenerId(listener.getInstanceId());
        return applicationLog;
    }


    @GetMapping(value = "log/creation", produces = "application/json")
    public List<String> getLogCreationTraces() {
        return ParentEntityListener.ON_CREATION_TRACES;
    }

    @GetMapping(value = "log/update", produces = "application/json")
    public List<String> getLogUpdateTraces() {
        return ParentEntityListener.ON_UPDATE_TRACES;
    }

    @GetMapping(value = "log/instance", produces = "application/json")
    public List<String> getLogInstanceTraces() {
        return ParentEntityListener.INSTANCE_CREATION_TRACES;
    }

    @GetMapping(value = "listener", produces = "application/json")
    public String getListenerId() {
        return "{ \"listenerId\": "+ listener.getInstanceId()+"}";
    }
}