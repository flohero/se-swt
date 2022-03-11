package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.Article;
import swt6.fhbay.repositories.ArticleRepository;
import swt6.fhbay.repositories.impl.base.JpaRepository;

import javax.persistence.EntityManager;

public class JpaArticleRepository extends JpaRepository<Article, Long> implements ArticleRepository {
    public JpaArticleRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Article> getPersistentClass() {
        return Article.class;
    }
}
