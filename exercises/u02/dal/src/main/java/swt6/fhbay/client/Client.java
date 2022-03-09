package swt6.fhbay.client;

import swt6.fhbay.domain.ArticleCategory;
import swt6.fhbay.repositories.impl.HibernateArticleCategoryRepository;
import swt6.fhbay.util.JpaUtil;

public class Client {

    public static void main(String[] args) {
        JpaUtil.getEntityManagerFactory();
        var em = JpaUtil.getTransactedEntityManager();
        var repo = new HibernateArticleCategoryRepository(em);
        var cat = new ArticleCategory("Dolphin");

        // when
        var res = repo.save(cat);

        JpaUtil.commit();
        JpaUtil.closeEntityManagerFactory();
    }
}
