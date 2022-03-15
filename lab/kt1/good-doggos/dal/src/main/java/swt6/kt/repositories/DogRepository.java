package swt6.kt.repositories;

import swt6.kt.domain.Dog;

import java.util.List;
import java.util.UUID;

public interface DogRepository extends Repository<Dog, UUID> {

    List<Dog> findByName(String name);
}
