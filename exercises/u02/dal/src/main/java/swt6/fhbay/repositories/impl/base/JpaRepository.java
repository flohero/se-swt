package swt6.fhbay.repositories.impl.base;

import swt6.fhbay.domain.EntityBase;
import swt6.fhbay.repositories.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class JpaRepository<T extends EntityBase, ID extends Long> implements Repository<T, ID> {
    private final EntityManager entityManager;

    protected JpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public abstract Class<T> getPersistentClass();

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(getEntityManager().find(getPersistentClass(), id));
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(getPersistentClass());
        Root<T> root = cr.from(getPersistentClass());
        cr.select(root);
        TypedQuery<T> query = entityManager.createQuery(cr);
        return query.getResultList();
    }

    @Override
    public long countAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        cr.select(cb.count(cr.from(getPersistentClass())));
        return entityManager
                .createQuery(cr)
                .getSingleResult();
    }

    @Override
    public T save(T entity) {
        return getEntityManager().merge(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    public int removeById(ID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        var cd = cb.createCriteriaDelete(getPersistentClass());
        Root<T> root = cd.from(getPersistentClass());
        cd.where(cb.equal(root.get("id"), id));
        return entityManager.createQuery(cd).executeUpdate();
    }
}
