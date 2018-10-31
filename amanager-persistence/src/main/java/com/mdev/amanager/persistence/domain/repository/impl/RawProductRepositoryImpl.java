package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.enums.UM;
import com.mdev.amanager.persistence.domain.model.RawProduct;
import com.mdev.amanager.persistence.domain.repository.RawProductRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityPersistenceException;
import com.mdev.amanager.persistence.domain.repository.exceptions.MultipleEntityFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Repository
@Transactional
public class RawProductRepositoryImpl extends BaseRepository<RawProduct> implements RawProductRepository {

    @Override
    public List<RawProduct> findByType(ProductType type) {
        return this
                .named("raw.product.find.by.type")
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<RawProduct> findByUm(UM um) {
        return this
                .named("raw.product.find.by.um")
                .setParameter("um", um)
                .getResultList();
    }

    @Override
    public RawProduct findByName(String name) throws EntityPersistenceException {
        try {
            return this
                    .named("raw.product.find.by.name")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException(e);
        } catch (NonUniqueResultException e) {
            throw new MultipleEntityFoundException(e);
        }
    }

    @Override
    public List<RawProduct> findByNamePattern(String namePattern) {
        return this
                .named("raw.product.find.by.name.pattern")
                .setParameter("name", like(namePattern))
                .getResultList();
    }

    @Override
    public List<RawProduct> findByNamePatternAndType(String namePattern, ProductType type) {
        return this
                .named("raw.product.find.by.name.pattern")
                .setParameter("name", like(namePattern))
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public Class<RawProduct> getManagedClass() {
        return RawProduct.class;
    }
}
