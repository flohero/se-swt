package com.example.services;

import com.example.dal.DogRepository;
import com.example.domain.Dog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DogService {

    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Transactional
    public Dog create(Dog dog) {
        return dogRepository.save(dog);
    }

    @Transactional(readOnly = true)
    public List<Dog> findAll() {
        return dogRepository.findAll();
    }
}
