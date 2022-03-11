package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.Category;
import swt6.fhbay.repositories.CategoryRepository;
import swt6.fhbay.repositories.impl.base.HibernateRepository;

import javax.persistence.EntityManager;

public class HibernateCategoryRepository
        extends HibernateRepository<Category, Long>
        implements CategoryRepository {

    public HibernateCategoryRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Category> getPersistentClass() {
        return Category.class;
    }

}
