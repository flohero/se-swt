package com.example.dal;

import com.example.domain.Dog;

import java.util.List;
import java.util.Optional;

public interface DogDao {
    Optional<Dog> findById(Long id);

    List<Dog> findAll();

    void remove(Dog dog);

    void insert(Dog dog);
}
