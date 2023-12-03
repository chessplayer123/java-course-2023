package edu.hw7.PersonDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockDatabase implements PersonDatabase {
    private final Map<Integer, Person> indexDb;
    private final Map<String, List<Person>> nameDb;
    private final Map<String, List<Person>> addressDb;
    private final Map<String, List<Person>> phoneDb;
    private final ReadWriteLock lock;

    public LockDatabase() {
        indexDb = new HashMap<>();
        nameDb = new HashMap<>();
        addressDb = new HashMap<>();
        phoneDb = new HashMap<>();

        lock = new ReentrantReadWriteLock();
    }

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
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
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            Person person = indexDb.remove(id);
            if (person != null) {
                nameDb.get(person.name()).remove(person);
                addressDb.get(person.address()).remove(person);
                phoneDb.get(person.phoneNumber()).remove(person);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
            return nameDb.getOrDefault(name, List.of());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
            return addressDb.getOrDefault(address, List.of());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
            return phoneDb.getOrDefault(phone, List.of());
        } finally {
            lock.readLock().unlock();
        }
    }
}
