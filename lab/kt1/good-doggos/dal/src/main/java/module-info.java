import swt6.kt.repositories.DogRepository;
import swt6.kt.repositories.impl.ListDogRepository;

module swt.kt.dal {
    exports swt6.kt.repositories;
    requires swt.kt.domain;
    provides DogRepository with ListDogRepository;
}