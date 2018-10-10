package com.mdev.amanager.persistence.domain.repository.base;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by gmilazzo on 01/10/2018.
 */
public abstract class BaseRepository<T extends Identifiable> implements Repository<T> {

    @PersistenceContext
    protected EntityManager em;

    public abstract Class<T> getManagedClass();

    @Transactional
    public void save(T item) {
        em.persist(item);
    }

    @Transactional
    public void delete(T item) {
        em.remove(item);
    }

    @Transactional
    public T merge(T item) {
        return em.merge(item);
    }

    @Transactional
    public T find(Long id) {
        return em.find(getManagedClass(), id);
    }

    @Transactional
    public List<T> find() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getManagedClass());
        Root<T> rootEntry = cq.from(getManagedClass());
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);

        return allQuery.getResultList();
    }

    protected String like(String param) {
        return "%" + param + "%";
    }

    protected TypedQuery<T> named(String query) {
        return em.createNamedQuery(query, getManagedClass());
    }
}
