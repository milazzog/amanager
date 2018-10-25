package com.mdev.amanager.persistence.domain.repository.base;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * Created by gmilazzo on 01/10/2018.
 */
public interface Repository<T extends Identifiable> {
    void save(T item);
    void delete(T item);
    T merge(T item);

    T find(Long id) throws EntityNotFoundException;
    List<T> find();
}