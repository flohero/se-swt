package swt6.fhbay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUtil {
    private static EntityManagerFactory emFactory;
    private static final ThreadLocal<EntityManager> emThread = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(JpaUtil.class);

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        logger.info("Creating EntityManagerFactory");
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory("fhbay");
        }
        return emFactory;
    }

    public static synchronized EntityManager getEntityManager() {
        logger.info("Creating EntityManager");
        if(emThread.get() == null) {
            emThread.set(emFactory.createEntityManager());
        }
        return emThread.get();
    }

    public static synchronized void closeEntityManager() {
        logger.info("Closing EntityManagerFactory");
        if(emThread.get() != null) {
            emThread.get().close();
            emThread.set(null);
        }
    }

    public static synchronized EntityManager getTransactedEntityManager() {
        logger.info("Creating transacted EntityManagerFactory");
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        if(!tx.isActive()) {
            tx.begin();
        }
        return em;
    }

    public static synchronized void commit() {
        logger.info("Committing");
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        if(tx.isActive()) {
            tx.commit();
        }
        closeEntityManager();
    }

    public static synchronized void rollback() {
        logger.info("Rolling back");
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        if(tx.isActive()) {
            tx.rollback();
        }
        closeEntityManager();
    }

    public static synchronized void closeEntityManagerFactory() {
        logger.info("Closing EntityManagerFactory");
        if (emFactory != null) {
            emFactory.close();
            emFactory = null;
        }
    }
}
