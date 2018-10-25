package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.FeeConfig;
import com.mdev.amanager.persistence.domain.repository.FeeConfigRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityPersistenceException;
import com.mdev.amanager.persistence.domain.repository.exceptions.MultipleEntityFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * Created by gmilazzo on 22/10/2018.
 */
@Repository("feeConfigRepository")
@Transactional
public class FeeConfigRepositoryImpl extends BaseRepository<FeeConfig> implements FeeConfigRepository {

    @Override
    public FeeConfig findByType(SubscriberType type) throws EntityPersistenceException {
        try {
            return this
                    .named("fee.config.find.by.type")
                    .setParameter("type", type)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException(e);
        } catch (NonUniqueResultException e) {
            throw new MultipleEntityFoundException(e);
        }
    }

    @Override
    public Class<FeeConfig> getManagedClass() {
        return FeeConfig.class;
    }
}
