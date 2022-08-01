package com.example.persistdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = PersistanceConfiguration.class)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    public void creation_shallSetCreationAndUpdateFields() {
        Person person = new Person();
        person.setFirstname("John");
        person.setLastname("Doe");

        repository.save(person);

        assertThat(person)
                .extracting("creationDate", "updateDate")
                .doesNotContainNull();

        assertThat(person.getCreationDate())
                .isEqualTo(person.getUpdateDate());
    }


    @Test
    public void update_shallSetCreationAndUpdateFields() throws InterruptedException {
        TestTransaction.flagForCommit();
        Person createdPerson = createPerson();
        assertThat(createdPerson)
                .isNotNull();
        TestTransaction.end();

        Thread.sleep(500);

        Person person = repository.findById(createdPerson.getId()).get();

        person.setFirstname("Jane");
        TestTransaction.start();
        repository.save(person);

        assertThat(person)
                .extracting("creationDate", "updateDate")
                .doesNotContainNull();

        assertThat(person.getCreationDate())
                .isBefore(person.getUpdateDate());
    }

    private Person createPerson() {
        Person person = new Person();
        person.setFirstname("John");
        person.setLastname("Doe");

        return repository.save(person);
    }
}