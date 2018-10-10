package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.model.User;
import com.mdev.amanager.persistence.domain.repository.base.Repository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;

/**
 * Created by gmilazzo on 01/10/2018.
 */
public interface UserRepository extends Repository<User> {

    User findByUsername(String username) throws EntityNotFoundException;
}
