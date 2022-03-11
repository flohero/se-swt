package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.BankAccount;
import swt6.fhbay.repositories.impl.base.HibernateRepository;

import javax.persistence.EntityManager;

public class HibernateBankAccountRepository extends HibernateRepository<BankAccount, Long> implements BankAccountRepository {

    protected HibernateBankAccountRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<BankAccount> getPersistentClass() {
        return BankAccount.class;
    }
}
