package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.model.Sequence;
import com.mdev.amanager.persistence.domain.repository.base.Repository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;

/**
 * Created by gmilazzo on 06/10/2018.
 */
public interface SequenceRepository extends Repository<Sequence> {

    Sequence findByName(String name) throws EntityNotFoundException;
}
