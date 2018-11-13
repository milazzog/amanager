package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.model.Product;
import com.mdev.amanager.persistence.domain.model.ProductPart;
import com.mdev.amanager.persistence.domain.repository.ProductPartRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gmilazzo on 06/11/2018.
 */
@Repository
@Transactional
public class ProductPartRepositoryImpl extends BaseRepository<ProductPart> implements ProductPartRepository {

    @Override
    public List<ProductPart> findByProduct(Product product) {
        return this
                .named("product.part.find.by.product")
                .setParameter("product", product)
                .getResultList();
    }

    @Override
    public Class<ProductPart> getManagedClass() {
        return ProductPart.class;
    }
}
