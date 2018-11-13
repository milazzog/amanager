package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.model.*;
import com.mdev.amanager.persistence.domain.repository.ProductRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import com.mdev.amanager.persistence.domain.repository.base.PredicateBuilder;
import com.mdev.amanager.persistence.domain.repository.params.ProductSearchParam;
import com.mdev.amanager.persistence.domain.repository.params.base.NumberMatcher;
import com.mdev.amanager.persistence.domain.repository.params.base.StringMatcher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by gmilazzo on 02/11/2018.
 */
@Repository
@Transactional
public class ProductRepositoryImpl extends BaseRepository<Product> implements ProductRepository {

    @Override
    public List<Product> findByName(String name) {
        return this
                .named("product.find.by.name")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Product> findByNamePattern(String name) {
        return this
                .named("product.find.by.name.pattern")
                .setParameter("name", like(name))
                .getResultList();
    }

    @Override
    public List<Product> findByNameAndType(String name, ProductType type) {
        return this
                .named("product.find.by.name.and.type")
                .setParameter("name", name)
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<Product> findByNamePatternAndType(String name, ProductType type) {
        return this
                .named("product.find.by.name.pattern.and.type")
                .setParameter("name", like(name))
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<Product> findBySearchParams(ProductSearchParam searchParam) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> ra = query.from(Product.class);
        Join<Product, Price> t = NumberMatcher.setted(searchParam.getPrice()) ? ra.join("prices") : null;

        Predicate[] p = PredicateBuilder
                .getInstance(cb)
                .append(searchParam.getName(), ra.get("name"))
                .append(searchParam.getType(), ra.get("type"))
                .append(searchParam.getPrice(), t, "amount")
                .appendNull(t, "removedAt")
                .end();

        query.where(p);

        TypedQuery<Product> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public Class<Product> getManagedClass() {
        return Product.class;
    }
}
