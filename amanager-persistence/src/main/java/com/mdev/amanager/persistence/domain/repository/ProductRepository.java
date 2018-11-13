package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.model.Product;
import com.mdev.amanager.persistence.domain.repository.base.Repository;
import com.mdev.amanager.persistence.domain.repository.params.ProductSearchParam;

import java.util.List;

/**
 * Created by gmilazzo on 02/11/2018.
 */
public interface ProductRepository extends Repository<Product> {

    List<Product> findByName(String name);

    List<Product> findByNamePattern(String name);

    List<Product> findByNameAndType(String name, ProductType type);

    List<Product> findByNamePatternAndType(String name, ProductType type);

    List<Product> findBySearchParams(ProductSearchParam searchParam);
}
