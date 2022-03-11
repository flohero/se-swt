package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.CreditCard;
import swt6.fhbay.repositories.CreditCardRepository;
import swt6.fhbay.repositories.impl.base.JpaRepository;

import javax.persistence.EntityManager;

public class JpaCreditCardRepository extends JpaRepository<CreditCard, Long> implements CreditCardRepository {
    public JpaCreditCardRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<CreditCard> getPersistentClass() {
        return CreditCard.class;
    }
}
