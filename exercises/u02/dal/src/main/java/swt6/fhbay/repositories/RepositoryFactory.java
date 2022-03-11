package swt6.fhbay.repositories;

import swt6.fhbay.repositories.impl.JpaBankAccountRepository;
import swt6.fhbay.repositories.impl.JpaCategoryRepository;
import swt6.fhbay.repositories.impl.JpaCreditCardRepository;
import swt6.fhbay.repositories.impl.JpaCustomerRepository;

import javax.persistence.EntityManager;

public class RepositoryFactory {
    private RepositoryFactory() {
        throw new IllegalStateException("Don't do that");
    }

    public static CustomerRepository getCustomerRepository(EntityManager entityManager) {
        return new JpaCustomerRepository(entityManager);
    }

    public static CreditCardRepository getCreditCardRepository(EntityManager entityManager) {
        return new JpaCreditCardRepository(entityManager);
    }

    public static BankAccountRepository getBankAccountRepository(EntityManager entityManager) {
        return new JpaBankAccountRepository(entityManager);
    }

    public static CategoryRepository getCategoryRepository(EntityManager entityManager) {
        return new JpaCategoryRepository(entityManager);
    }
}
