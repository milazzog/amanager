package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.model.Price;
import com.mdev.amanager.persistence.domain.model.Product;
import com.mdev.amanager.persistence.domain.repository.PriceRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gmilazzo on 06/11/2018.
 */
@Repository
@Transactional
public class PriceRepositoryImpl extends BaseRepository<Price> implements PriceRepository {

    @Override
    public List<Price> findByProduct(Product product) {
        return this
                .named("price.find.by.product")
                .setParameter("product", product)
                .getResultList();
    }

    @Override
    public Class<Price> getManagedClass() {
        return Price.class;
    }
}
