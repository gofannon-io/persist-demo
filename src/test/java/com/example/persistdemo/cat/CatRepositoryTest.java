package com.example.persistdemo.cat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.transaction.TestTransaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CatRepositoryTest {

    @Autowired
    private CatRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void checkInitialCatsAreLoaded() {
        assertThat(repository.count())
                .isEqualTo(2);
    }

    @Test
    public void createSingleCat_shallCallOncePrePersist() {
        int initialCreationTraceCount = CatEntityListener.ON_CREATION_TRACES.size();

        repository.save(new Cat(1, "Félix"));

        assertThat(CatEntityListener.ON_CREATION_TRACES)
                .hasSize(initialCreationTraceCount + 1);
    }

    @Test
    public void createTwoCat_shallCallTwicePrePersist() {
        int initialCreationTraceCount = CatEntityListener.ON_CREATION_TRACES.size();

        repository.save(new Cat(1, "Félix"));
        repository.save(new Cat(2, "Tom"));

        assertThat(CatEntityListener.ON_CREATION_TRACES)
                .hasSize(initialCreationTraceCount + 2);
    }

    @Test
    public void updateSingleCat_shallCallOncePreUpdate() {
        TestTransaction.flagForCommit();
        int initialCreationTraceCount = CatEntityListener.ON_CREATION_TRACES.size();
        int initialUpdateTraceCount = CatEntityListener.ON_UPDATE_TRACES.size();

        Cat cat = repository.findById(100).orElseThrow();
        assertThat( cat.getName())
                .isEqualTo("Sylvestre");

        TestTransaction.end();
        TestTransaction.start();


        cat.setName("Seth");
        repository.save(cat);
        TestTransaction.end();

        assertThat(CatEntityListener.ON_CREATION_TRACES)
                .hasSize(initialCreationTraceCount);
        assertThat(CatEntityListener.ON_UPDATE_TRACES)
                .contains("100|Seth")
                .hasSize(initialUpdateTraceCount+1);
    }

}