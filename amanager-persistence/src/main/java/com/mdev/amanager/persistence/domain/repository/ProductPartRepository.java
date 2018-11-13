package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.model.Product;
import com.mdev.amanager.persistence.domain.model.ProductPart;
import com.mdev.amanager.persistence.domain.repository.base.Repository;

import java.util.List;

/**
 * Created by gmilazzo on 06/11/2018.
 */
public interface ProductPartRepository extends Repository<ProductPart> {

    List<ProductPart> findByProduct(Product product);
}
