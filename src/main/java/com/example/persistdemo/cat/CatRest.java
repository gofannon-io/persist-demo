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

package com.example.persistdemo.cat;

import com.example.persistdemo.common.ErrorResponse;
import com.example.persistdemo.common.PetUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cats")
public class CatRest {

    private CatRepository repository;

    @Autowired
    public void setRepository(CatRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = "application/json")
    public List<Cat> getAll() {
        List<Cat> allCats = new ArrayList<>();
        repository.findAll().forEach(allCats::add);
        return allCats;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Cat inCreationCat) {
        if (inCreationCat.getId() <= 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The Cat id '" + inCreationCat.getId() + "' is invalid"));
        }

        Optional<Cat> existingCatOption = repository.findById(inCreationCat.getId());
        if (existingCatOption.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("A cat with id '" + inCreationCat.getId() + "' already exists"));
        }

        Cat createdCat = repository.save(inCreationCat);
        return ResponseEntity.ok(createdCat);
    }

    @PutMapping(value = "{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody PetUpdate petUpdate) {
        int idAsInt;
        try {
            idAsInt = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Cat's id '" + id + "' is not valid"));
        }

        Optional<Cat> optionalCat = repository.findById(idAsInt);
        if (optionalCat.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Cat with id " + id + " is not found"));
        }

        Cat cat = optionalCat.get();
        cat.setName(petUpdate.getName());
        repository.save(cat);
        return ResponseEntity.ok(cat);
    }

    @GetMapping(value = "log/creation", produces = "application/json")
    public List<String> getLogCreationTraces() {
        return CatEntityListener.ON_CREATION_TRACES;
    }

    @GetMapping(value = "log/update", produces = "application/json")
    public List<String> getLogUpdateTraces() {
        return CatEntityListener.ON_UPDATE_TRACES;
    }

}