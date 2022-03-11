package swt6.fhbay.client;

import swt6.fhbay.domain.Category;
import swt6.fhbay.repositories.impl.HibernateCategoryRepository;
import swt6.fhbay.util.JpaUtil;

public class Client {

    public static void main(String[] args) {
        JpaUtil.getEntityManagerFactory();
        var em = JpaUtil.getTransactedEntityManager();
        var repo = new HibernateCategoryRepository(em);
        var cat = new Category("Dolphin");

        // when
        var res = repo.save(cat);

        JpaUtil.commit();
        JpaUtil.closeEntityManagerFactory();
    }
}