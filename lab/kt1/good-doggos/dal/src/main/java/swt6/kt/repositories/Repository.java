package swt6.kt.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T, ID extends UUID> {
    T save(T entity);
    boolean remove(T entity);
    List<T> findAll();
    Optional<T> findById(ID id);
}
