package swt6.kt.client;

import swt6.kt.domain.Dog;
import swt6.kt.repositories.DogRepository;

import java.util.ServiceLoader;

public class Client {
    private static DogRepository getDogRepository() {
        ServiceLoader<DogRepository> sl = ServiceLoader.load(DogRepository.class);
        for (var repo : sl) {
            return repo;
        }
        return null;
    }


    public static void main(String[] args) {
        Dog samwise = new Dog("Samwise");
        System.out.println(samwise);
        var repo = getDogRepository();
        assert repo != null;
        samwise = repo.save(samwise);
        System.out.println(samwise);
    }
}
