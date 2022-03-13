package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.Article;
import swt6.fhbay.domain.Article_;
import swt6.fhbay.repositories.ArticleRepository;
import swt6.fhbay.repositories.impl.base.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class JpaArticleRepository extends JpaRepository<Article, Long> implements ArticleRepository {
    public JpaArticleRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Article> getPersistentClass() {
        return Article.class;
    }


    @Override
    public List<Article> findWereNameOrDescriptionContains(String text) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        var cd = cb.createQuery(getPersistentClass());
        var root = cd.from(getPersistentClass());
        String pattern = "%" + text + "%";
        cd.where(
                cb.or(
                        cb.like(root.get(Article_.NAME), pattern),
                        cb.like(root.get(Article_.DESCRIPTION), pattern)
                )
        );
        return getEntityManager().createQuery(cd).getResultList();
    }
}
