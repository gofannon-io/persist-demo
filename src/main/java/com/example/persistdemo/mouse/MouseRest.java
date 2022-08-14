/*
 * Copyright (c) 2022 gofannon.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.persistdemo.mouse;

import com.example.persistdemo.common.ApplicationLog;
import com.example.persistdemo.common.ErrorResponse;
import com.example.persistdemo.common.PetUpdate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("mice")
public class MouseRest implements ApplicationContextAware {

    private MouseRepository repository;

    private ParentEntityListener listener;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.listener = applicationContext.getBean(ParentEntityListener.class);
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
}