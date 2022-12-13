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

package com.example.persistdemo.parrot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ParrotRepositoryTest {

    @Autowired
    private ParrotRepository repository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        ParentEntityListener.ON_CREATION_TRACES.clear();
        ParentEntityListener.ON_UPDATE_TRACES.clear();
    }


    @Test
    @DisplayName("Shall call onCreation when an entity is created")
    public void save_shallCallOnCreationWhenSavingANewEntity() {
        Parrot parrot = new Parrot(1, "Echo");

        repository.save(parrot);

        assertThat(ParentEntityListener.ON_CREATION_TRACES)
                .hasSize(1)
                .contains("Parrot{id=1,name='Echo'}");
        assertThat(ParentEntityListener.ON_UPDATE_TRACES)
                .isEmpty();
    }


    @Test
    @DisplayName("Shall call onUpdate when an entity is updated")
    public void save_shallCallOnUpdateWhenSavingAnExistingEntity() {
        assertThat(repository.existsById(100))
                .isTrue();
        Parrot parrot = new Parrot(100, "Echo");

        repository.save(parrot);
        entityManager.flush();

        assertThat(ParentEntityListener.ON_CREATION_TRACES)
                .isEmpty();

        assertThat(ParentEntityListener.ON_UPDATE_TRACES)
                .hasSize(1)
                .contains("Parrot{id=100,name='Echo'}");
    }


    @Test
    @DisplayName("Shall call onUpdate when an entity is updated (2)")
    public void save_2_shallCallOnUpdateWhenSavingAnExistingEntity() {
        Parrot parrot = new Parrot(10, "Péco");
        repository.save(parrot);
        entityManager.flush();


        Parrot parrot2 = new Parrot(10, "Pépé");
        repository.save(parrot2);
        entityManager.flush();


        assertThat(ParentEntityListener.ON_CREATION_TRACES)
                .hasSize(1)
                .contains("Parrot{id=10,name='Péco'}");

        assertThat(ParentEntityListener.ON_UPDATE_TRACES)
                .hasSize(1)
                .contains("Parrot{id=10,name='Pépé'}");
    }
}