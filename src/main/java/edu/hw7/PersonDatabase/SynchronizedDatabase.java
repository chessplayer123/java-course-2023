package edu.hw7.PersonDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SynchronizedDatabase implements PersonDatabase {
    private final Map<Integer, Person> indexDb;
    private final Map<String, List<Person>> nameDb;
    private final Map<String, List<Person>> addressDb;
    private final Map<String, List<Person>> phoneDb;

    public SynchronizedDatabase() {
        indexDb = new HashMap<>();
        nameDb = new HashMap<>();
        addressDb = new HashMap<>();
        phoneDb = new HashMap<>();
    }

    @Override
    public synchronized void add(Person person) {
        Person prevPerson = indexDb.get(person.id());
        if (prevPerson != null) {
            nameDb.get(prevPerson.name()).remove(prevPerson);
            addressDb.get(prevPerson.address()).remove(prevPerson);
            phoneDb.get(prevPerson.phoneNumber()).remove(prevPerson);
        }

        indexDb.put(person.id(), person);

        nameDb.putIfAbsent(person.name(), new ArrayList<>());
        nameDb.get(person.name()).add(person);

        addressDb.putIfAbsent(person.address(), new ArrayList<>());
        addressDb.get(person.address()).add(person);

        phoneDb.putIfAbsent(person.phoneNumber(), new ArrayList<>());
        phoneDb.get(person.phoneNumber()).add(person);
    }

    @Override
    public synchronized void delete(int id) {
        Person person = indexDb.remove(id);
        if (person != null) {
            nameDb.get(person.name()).remove(person);
            addressDb.get(person.address()).remove(person);
            phoneDb.get(person.phoneNumber()).remove(person);
        }
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return nameDb.getOrDefault(name, List.of());
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return addressDb.getOrDefault(address, List.of());
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return phoneDb.getOrDefault(phone, List.of());
    }
}
