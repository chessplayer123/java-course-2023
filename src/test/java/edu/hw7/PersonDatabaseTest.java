package edu.hw7;

import edu.hw7.PersonDatabase.LockDatabase;
import edu.hw7.PersonDatabase.Person;
import edu.hw7.PersonDatabase.PersonDatabase;
import edu.hw7.PersonDatabase.SynchronizedDatabase;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.List;

public class PersonDatabaseTest {
    @RepeatedTest(128)
    void addingToLockDatabaseIsAtomic() throws InterruptedException {
        PersonDatabase db = new LockDatabase();
        Person addedPerson = new Person(0, "Mitchell", "Goodsprings", "-");

        new Thread(() -> db.add(addedPerson)).start();
        Thread.sleep(10); // switch context

        List<Person> findByNames = db.findByName(addedPerson.name());
        List<Person> findByAddress = db.findByAddress(addedPerson.address());
        List<Person> findByPhone = db.findByPhone(addedPerson.phoneNumber());

        assertThat(findByNames)
            .isEqualTo(findByAddress)
            .isEqualTo(findByPhone)
            .containsOnly(addedPerson);
    }

    @RepeatedTest(128)
    void addingToSynchronizedDatabaseIsAtomic() throws InterruptedException {
        PersonDatabase db = new SynchronizedDatabase();
        Person addedPerson = new Person(0, "Mitchell", "Goodsprings", "-");

        new Thread(() -> db.add(addedPerson)).start();
        Thread.sleep(10); // switch context

        List<Person> findByNames = db.findByName(addedPerson.name());
        List<Person> findByAddress = db.findByAddress(addedPerson.address());
        List<Person> findByPhone = db.findByPhone(addedPerson.phoneNumber());

        assertThat(findByNames)
            .isEqualTo(findByAddress)
            .isEqualTo(findByPhone)
            .containsOnly(addedPerson);
    }

    @RepeatedTest(128)
    void removeFromSynchronizedDatabaseIsAtomic() throws InterruptedException {
        PersonDatabase db = new SynchronizedDatabase();
        Person addedPerson = new Person(0, "Mitchell", "Goodsprings", "-");

        db.add(addedPerson);
        new Thread(() -> db.delete(addedPerson.id())).start();
        Thread.sleep(10); // switch context

        List<Person> findByNames = db.findByName(addedPerson.name());
        List<Person> findByAddress = db.findByAddress(addedPerson.address());
        List<Person> findByPhone = db.findByPhone(addedPerson.phoneNumber());

        assertThat(findByNames)
            .isEqualTo(findByAddress)
            .isEqualTo(findByPhone)
            .isEmpty();
    }

    @RepeatedTest(128)
    void removeFromLockDatabaseIsAtomic() throws InterruptedException {
        PersonDatabase db = new LockDatabase();
        Person addedPerson = new Person(0, "Mitchell", "Goodsprings", "-");

        db.add(addedPerson);
        new Thread(() -> db.delete(addedPerson.id())).start();
        Thread.sleep(10); // switch context

        List<Person> findByNames = db.findByName(addedPerson.name());
        List<Person> findByAddress = db.findByAddress(addedPerson.address());
        List<Person> findByPhone = db.findByPhone(addedPerson.phoneNumber());

        assertThat(findByNames)
            .isEqualTo(findByAddress)
            .isEqualTo(findByPhone)
            .isEmpty();
    }

    @RepeatedTest(128)
    void addingToLockDatabasePresentId_shouldOverridePrevPerson() throws InterruptedException {
        PersonDatabase db = new LockDatabase();
        Person prevPerson = new Person(0, "Mitchell", "Goodsprings", "-");
        Person newPerson = new Person(0, "Victor", "Goodsprings", "2060");

        new Thread(() -> db.add(prevPerson)).start();
        Thread.sleep(10); // switch context

        db.add(newPerson);
        List<Person> actualPrevPersonNameSearch = db.findByName(prevPerson.name());
        List<Person> actualAddressSearch = db.findByAddress(newPerson.address());

        assertThat(actualAddressSearch).containsOnly(newPerson);
        assertThat(actualPrevPersonNameSearch).isEmpty();
    }

    @RepeatedTest(128)
    void addingToSynchronizedDatabasePresentId_shouldOverridePrevPerson() throws InterruptedException {
        PersonDatabase db = new SynchronizedDatabase();
        Person prevPerson = new Person(0, "Mitchell", "Goodsprings", "-");
        Person newPerson = new Person(0, "Victor", "Goodsprings", "2060");

        new Thread(() -> db.add(prevPerson)).start();
        Thread.sleep(10); // switch context

        db.add(newPerson);
        List<Person> actualPrevPersonNameSearch = db.findByName(prevPerson.name());
        List<Person> actualAddressSearch = db.findByAddress(newPerson.address());

        assertThat(actualAddressSearch).containsOnly(newPerson);
        assertThat(actualPrevPersonNameSearch).isEmpty();
    }

    @Test
    void findInLockDatabase_shouldReturnAllPresentPersons() {
        PersonDatabase db = new LockDatabase();

        List<Person> addedPersons = List.of(
            new Person(0, "Mitchell", "Goodsprings", "-"),
            new Person(1, "Mitchell", "California", "112233"),
            new Person(4, "House", "Vegas", "332211")
        );

        addedPersons.parallelStream().forEach(db::add);

        List<Person> actualPersonsByName = db.findByName("Mitchell");
        Person[] expectedPersonsByName = new Person[] {
            addedPersons.get(0),
            addedPersons.get(1)
        };

        assertThat(actualPersonsByName)
            .containsOnly(expectedPersonsByName);
    }

    @Test
    void findInSynchronizedDatabase_shouldReturnAllPresentPersons() {
        PersonDatabase db = new SynchronizedDatabase();

        List<Person> addedPersons = List.of(
            new Person(0, "Mitchell", "Goodsprings", "-"),
            new Person(1, "Mitchell", "California", "112233"),
            new Person(4, "House", "Vegas", "332211")
        );

        addedPersons.parallelStream().forEach(db::add);

        List<Person> actualPersonsByName = db.findByName("Mitchell");
        Person[] expectedPersonsByName = new Person[] {
            addedPersons.get(0),
            addedPersons.get(1)
        };

        assertThat(actualPersonsByName)
            .containsOnly(expectedPersonsByName);
    }
}
