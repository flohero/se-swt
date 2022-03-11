package swt6.fhbay.repositories;

import swt6.fhbay.repositories.impl.HibernateBankAccountRepository;
import swt6.fhbay.repositories.impl.HibernateCategoryRepository;
import swt6.fhbay.repositories.impl.HibernateCreditCardRepository;
import swt6.fhbay.repositories.impl.HibernateCustomerRepository;

import javax.persistence.EntityManager;

public class RepositoryFactory {
    private RepositoryFactory() {
        throw new IllegalStateException("Don't do that");
    }

    public static CustomerRepository getCustomerRepository(EntityManager entityManager) {
        return new HibernateCustomerRepository(entityManager);
    }

    public static CreditCardRepository getCreditCardRepository(EntityManager entityManager) {
        return new HibernateCreditCardRepository(entityManager);
    }

    public static BankAccountRepository getBankAccountRepository(EntityManager entityManager) {
        return new HibernateBankAccountRepository(entityManager);
    }

    public static CategoryRepository getCategoryRepository(EntityManager entityManager) {
        return new HibernateCategoryRepository(entityManager);
    }
}
