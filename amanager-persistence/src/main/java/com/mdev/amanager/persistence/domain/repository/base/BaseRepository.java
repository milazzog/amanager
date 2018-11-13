package com.mdev.amanager.persistence.domain.repository.base;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;
import com.mdev.amanager.persistence.domain.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * Created by gmilazzo on 01/10/2018.
 */
public abstract class BaseRepository<T extends Identifiable> implements Repository<T> {

    @PersistenceContext
    protected EntityManager em;

    public abstract Class<T> getManagedClass();

    @Transactional
    public void save(T item) {

        em.persist(ReflectionUtil.uppercaseAllString(item));
    }

    @Transactional
    public void delete(T item) {
        em.remove(item);
    }

    @Transactional
    public T merge(T item) {

        return em.merge(ReflectionUtil.uppercaseAllString(item));
    }

    @Transactional
    public T find(Long id) throws EntityNotFoundException {
        T r = em.find(getManagedClass(), id);
        if (Objects.isNull(r)) {
            throw new EntityNotFoundException(String.format("no %s with id [%09d] found.", getManagedClass().getSimpleName(), id));
        }
        return r;
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
