package swt6.fhbay.repositories.impl;

import swt6.fhbay.domain.Customer;
import swt6.fhbay.repositories.CustomerRepository;
import swt6.fhbay.repositories.impl.base.HibernateRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class HibernateCustomerRepository extends HibernateRepository<Customer, Long> implements CustomerRepository {
    public HibernateCustomerRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Customer> getPersistentClass() {
        return Customer.class;
    }

    @Override
    public List<Customer> findByLastname(String name) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        var cd = cb.createQuery(getPersistentClass());
        var root = cd.from(getPersistentClass());
        cd.where(cb.like(root.get("lastname"), name));
        return getEntityManager().createQuery(cd).getResultList();
    }
}
