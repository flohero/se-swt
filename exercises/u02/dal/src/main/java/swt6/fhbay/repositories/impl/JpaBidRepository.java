package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.Article;
import swt6.fhbay.domain.Article_;
import swt6.fhbay.domain.Bid;
import swt6.fhbay.domain.Bid_;
import swt6.fhbay.repositories.BidRepository;
import swt6.fhbay.repositories.impl.base.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class JpaBidRepository extends JpaRepository<Bid, Long> implements BidRepository {

    public JpaBidRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Bid> getPersistentClass() {
        return Bid.class;
    }

    @Override
    public Optional<Bid> findSecondHighestBidForArticle(Article article) {
        TypedQuery<Bid> query = getFindBidsByArticleOrderedByAmountDescQuery(article);
        query.setFirstResult(1);
        query.setMaxResults(1);
        return query.getResultStream().findFirst();
    }

    @Override
    public Optional<Bid> findHighestBidForArticle(Article article) {
        TypedQuery<Bid> query = getFindBidsByArticleOrderedByAmountDescQuery(article);
        query.setFirstResult(0);
        query.setMaxResults(1);
        return query.getResultStream().findFirst();
    }

    private TypedQuery<Bid> getFindBidsByArticleOrderedByAmountDescQuery(Article article) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Bid> cr = cb.createQuery(getPersistentClass());
        Root<Bid> root = cr.from(getPersistentClass());

        var join = root.join(Bid_.article);
        cr.select(root)
                .where(cb.equal(join.get(Article_.ID), article.getId()))
                .orderBy(
                        List.of(
                                cb.desc(root.get(Bid_.AMOUNT)),
                                cb.asc(root.get(Bid_.DATE))
                        )
                );

        return getEntityManager().createQuery(cr);
    }

}
