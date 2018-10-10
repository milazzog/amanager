package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.model.User;
import com.mdev.amanager.persistence.domain.repository.UserRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.Objects;

/**
 * Created by gmilazzo on 01/10/2018.
 */
@Repository
@Transactional
public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {

    public Class<User> getManagedClass() {
        return User.class;
    }


    @Override
    public User findByUsername(String username) throws EntityNotFoundException{

        try {
            return this
                    .named("user.find.by.username")
                    .setParameter("username", username)
                    .setMaxResults(1)
                    .getSingleResult();
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(e);
        }
    }
}
