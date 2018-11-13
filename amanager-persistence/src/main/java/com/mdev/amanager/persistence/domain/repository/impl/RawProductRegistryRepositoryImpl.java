package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.model.RawProductRegistry;
import com.mdev.amanager.persistence.domain.repository.RawProductRegistryRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gmilazzo on 02/11/2018.
 */
@Repository
@Transactional
public class RawProductRegistryRepositoryImpl extends BaseRepository<RawProductRegistry> implements RawProductRegistryRepository {

    @Override
    public Class<RawProductRegistry> getManagedClass() {
        return RawProductRegistry.class;
    }
}
