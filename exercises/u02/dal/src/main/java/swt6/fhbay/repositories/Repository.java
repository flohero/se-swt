package swt6.fhbay.repositories;

import swt6.fhbay.domain.EntityBase;

import java.util.List;

public interface Repository<T extends EntityBase, ID extends Long> {
    T findById(ID id);

    List<T> findAll();

    long countAll();

    T save(T entity);

    void remove(T entity);

    int removeById(ID id);
}
