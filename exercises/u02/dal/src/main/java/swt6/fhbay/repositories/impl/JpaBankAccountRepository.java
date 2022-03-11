package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.BankAccount;
import swt6.fhbay.repositories.BankAccountRepository;
import swt6.fhbay.repositories.impl.base.JpaRepository;

import javax.persistence.EntityManager;

public class JpaBankAccountRepository extends JpaRepository<BankAccount, Long> implements BankAccountRepository {

    public JpaBankAccountRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<BankAccount> getPersistentClass() {
        return BankAccount.class;
    }
}
