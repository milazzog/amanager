package com.mdev.amanager.web.service;

import com.mdev.amanager.persistence.domain.model.User;
import com.mdev.amanager.persistence.domain.repository.UserRepository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mdev.amanager.web.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by gmilazzo on 01/10/2018.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        try {
            logger.info(String.format("looking for %s with username %s", User.class.getSimpleName(), s));
            User u = userRepository.findByUsername(s);
            logger.info(String.format("%s with username %s successfully retrieved", User.class.getSimpleName(), s));
            return new UserDetailsImpl(u);
        } catch (EntityNotFoundException e) {
            logger.error(String.format("error while retrieving %s:", User.class.getSimpleName()), e);
            throw new UsernameNotFoundException(String.format("no %s found with username '%s'", User.class.getSimpleName(), s), e);
        }
    }
}
