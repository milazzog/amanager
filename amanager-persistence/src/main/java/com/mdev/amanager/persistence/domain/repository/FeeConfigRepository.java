package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.FeeConfig;
import com.mdev.amanager.persistence.domain.repository.base.Repository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityPersistenceException;

/**
 * Created by gmilazzo on 22/10/2018.
 */
public interface FeeConfigRepository extends Repository<FeeConfig> {

    FeeConfig findByType(SubscriberType type) throws EntityPersistenceException;
}
