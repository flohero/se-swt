package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.Category;
import swt6.fhbay.repositories.CategoryRepository;
import swt6.fhbay.repositories.impl.base.JpaRepository;

import javax.persistence.EntityManager;

public class JpaCategoryRepository
        extends JpaRepository<Category, Long>
        implements CategoryRepository {

    public JpaCategoryRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Category> getPersistentClass() {
        return Category.class;
    }

}
