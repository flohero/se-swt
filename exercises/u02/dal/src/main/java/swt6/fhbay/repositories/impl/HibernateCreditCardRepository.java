package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.CreditCard;
import swt6.fhbay.repositories.CreditCardRepository;
import swt6.fhbay.repositories.impl.base.HibernateRepository;

import javax.persistence.EntityManager;

public class HibernateCreditCardRepository extends HibernateRepository<CreditCard, Long> implements CreditCardRepository {
    protected HibernateCreditCardRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<CreditCard> getPersistentClass() {
        return CreditCard.class;
    }
}
