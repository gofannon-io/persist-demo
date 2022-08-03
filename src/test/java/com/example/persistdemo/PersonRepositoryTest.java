package com.example.persistdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = PersistanceConfiguration.class)
class PersonRepositoryTest {

    private AtomicInteger PERSON_ID_GENERATOR = new AtomicInteger();

    @Autowired
    private PersonRepository repository;


    @Test
    public void creation_shallSetCreationAndUpdateFields() {
        int initialInstanceCount = AbstractEntityListener.INSTANCE_COUNT.get();

        Person person = new Person("John","Doe");

        repository.save(person);

        assertThat(person)
                .extracting("creationDate", "updateDate")
                .doesNotContainNull();

        assertThat(person.getCreationDate())
                .isEqualTo(person.getUpdateDate());

        assertThat(AbstractEntityListener.INSTANCE_COUNT.get())
                .isEqualTo(initialInstanceCount);
    }


    @Test
    public void update_shallSetUpdateFields() throws Exception {
        TestTransaction.flagForCommit();

//        assertThat(TestTransaction.isActive()).isFalse();
//        int initialInstanceCount = AbstractEntityListener.INSTANCE_COUNT.get();
        assertThat(TestTransaction.isActive()).isTrue();

        Person createdPerson = repository.save(createPerson("John", "Doe"));
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(createdPerson)
                .isNotNull();

        Thread.sleep(500);

        TestTransaction.start();
        Optional<Person> optionalPerson = repository.findById(createdPerson.getId());
        Person person = optionalPerson.orElseThrow(AssertionError::new);
//        Person person = createdPerson;

        person.setFirstname("Jane");
        repository.save(person);

        assertThat(person)
                .extracting("creationDate", "updateDate")
                .doesNotContainNull();

        assertThat(person.getCreationDate())
                .isBefore(person.getUpdateDate());

        TestTransaction.end();

//        assertThat(AbstractEntityListener.INSTANCE_COUNT.get())
//                .isEqualTo(initialInstanceCount);
    }

    private Person createPerson(String firstname, String lastname) {
        Person person = new Person(firstname, lastname);
        person.setId(PERSON_ID_GENERATOR.incrementAndGet());
        return person;
    }

    @Test
    public void checkThatO() {
        int initialInstanceCount = AbstractEntityListener.INSTANCE_COUNT.get();
         repository.save(new Person("John", "Doe"));
         repository.save(new Person("Jane", "Doe"));

         assertThat(AbstractEntityListener.INSTANCE_COUNT.get())
                 .isEqualTo(initialInstanceCount);
    }
}