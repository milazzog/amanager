package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.enums.UM;
import com.mdev.amanager.persistence.domain.model.RawProduct;
import com.mdev.amanager.persistence.domain.repository.base.Repository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityPersistenceException;

import java.util.List;

/**
 * Created by gmilazzo on 25/10/2018.
 */
public interface RawProductRepository extends Repository<RawProduct> {

    List<RawProduct> findByType(ProductType type);

    List<RawProduct> findByUm(UM um);

    RawProduct findByName(String name) throws EntityPersistenceException;

    List<RawProduct> findByNamePattern(String namePattern);

    List<RawProduct> findByNamePatternAndType(String namePattern, ProductType type);
}
