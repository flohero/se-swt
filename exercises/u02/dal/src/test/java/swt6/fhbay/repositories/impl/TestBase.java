package swt6.fhbay.repositories.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import swt6.fhbay.util.JpaUtil;

import javax.persistence.EntityManager;

public class TestBase {
    protected EntityManager em;

    @BeforeAll
    static void setUpAll() {
        JpaUtil.getEntityManagerFactory();
    }

    @AfterAll
    static void tearDownAll() {
        JpaUtil.closeEntityManagerFactory();
    }

    @BeforeEach
    void setUp() {
        em = JpaUtil.getTransactedEntityManager();
    }

    @AfterEach
    void tearDown() {
        JpaUtil.rollback();
    }
}
