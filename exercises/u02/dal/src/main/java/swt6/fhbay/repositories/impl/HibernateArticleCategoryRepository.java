package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.ArticleCategory;
import swt6.fhbay.repositories.impl.base.HibernateRepository;
import swt6.fhbay.repositories.ArticleCategoryRepository;

import javax.persistence.EntityManager;

public class HibernateArticleCategoryRepository
        extends HibernateRepository<ArticleCategory, Long>
        implements ArticleCategoryRepository {

    public HibernateArticleCategoryRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<ArticleCategory> getPersistentClass() {
        return ArticleCategory.class;
    }

}
