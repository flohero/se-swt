package swt6.fhbay;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import swt6.fhbay.domain.ArticleCategory;
import swt6.fhbay.repositories.impl.HibernateArticleCategoryRepository;
import swt6.fhbay.util.JpaUtil;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HibernateArticleCategoryRepositoryTest {
    private EntityManager em;

    @BeforeEach
    void setUp() {
        JpaUtil.getEntityManagerFactory();
        em = JpaUtil.getTransactedEntityManager();
    }

    @AfterEach
    void tearDown() {
        JpaUtil.rollback();
        JpaUtil.closeEntityManagerFactory();
    }

    @Test
    void getPersistentClass() {
        // given
        var repo = new HibernateArticleCategoryRepository(null);
        // when
        var persistentClass = repo.getPersistentClass();
        // then
        assertEquals(ArticleCategory.class, persistentClass);
    }

    @Test
    void insertSingleCategory() {
        // given
        var repo = new HibernateArticleCategoryRepository(em);
        var cat = new ArticleCategory("Dolphin");

        // when
        var res = repo.save(cat);

        //then
        assertNotNull(res.getId());

    }
}