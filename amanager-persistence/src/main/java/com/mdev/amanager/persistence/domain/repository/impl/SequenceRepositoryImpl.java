package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.model.Sequence;
import com.mdev.amanager.persistence.domain.repository.SequenceRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@Repository
@Transactional
public class SequenceRepositoryImpl extends BaseRepository<Sequence> implements SequenceRepository{

    @Override
    public Sequence findByName(String name) throws EntityNotFoundException{

        try {

        return this
                .named("sequence.find.by.name")
                .setParameter("name", name)
                .setMaxResults(1)
                .getSingleResult();
        }catch (Exception e){
            throw new EntityNotFoundException(e);
        }
    }

    @Override
    public Class<Sequence> getManagedClass() {
        return Sequence.class;
    }
}
