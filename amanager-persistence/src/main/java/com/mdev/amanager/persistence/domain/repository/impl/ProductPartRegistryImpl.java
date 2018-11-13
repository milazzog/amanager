package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.model.ProductPartRegistry;
import com.mdev.amanager.persistence.domain.repository.ProductPartRegistryRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gmilazzo on 13/11/2018.
 */
@Repository
@Transactional
public class ProductPartRegistryImpl extends BaseRepository<ProductPartRegistry> implements ProductPartRegistryRepository {

    @Override
    public Class<ProductPartRegistry> getManagedClass() {
        return ProductPartRegistry.class;
    }
}
