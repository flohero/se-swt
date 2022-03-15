package swt6.kt.repositories.impl;

import swt6.kt.domain.Dog;
import swt6.kt.repositories.DogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListDogRepository implements DogRepository {
    private final List<Dog> dogs = new CopyOnWriteArrayList<>();

    @Override
    public List<Dog> findByName(String name) {
        return dogs.stream()
                .filter(d -> d.getName().equals(name))
                .toList();
    }

    @Override
    public Dog save(Dog entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Dog cannot be null");
        }
        if(entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        var found = findById(entity.getId());
        if(found.isEmpty()) {
            dogs.add(entity);
            return entity;
        }
        found.get().setName(entity.getName());
        return found.get();
    }

    @Override
    public boolean remove(Dog entity) {
        return dogs.remove(entity);
    }

    @Override
    public List<Dog> findAll() {
        return new ArrayList<>(dogs);
    }

    @Override
    public Optional<Dog> findById(UUID uuid) {
        for(var dog : dogs) {
            if(dog.getId().equals(uuid)) {
                return Optional.of(dog);
            }
        }
        return Optional.empty();
    }
}
