package com.mdev.amanager.core.service;

import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.persistence.domain.model.RawProductRegistry;
import com.mdev.amanager.persistence.domain.repository.RawProductRegistryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * Created by gmilazzo on 02/11/2018.
 */
@Service
public class RawProductRegistryService {

    private static final Logger logger = LogManager.getLogger(RawProductRegistryService.class);

    @Autowired
    private RawProductRegistryRepository rawProductRegistryRepository;

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public RawProductRegistry create(RawProductRegistry rawProductRegistry) throws ServiceException {

        if (Objects.isNull(rawProductRegistry)) {
            throw new ServiceException(ServiceException.NULL_OBJECT);
        }

        try {

            rawProductRegistry.setAddedAt(new Date());
            rawProductRegistry.setOver(false);
            rawProductRegistryRepository.save(rawProductRegistry);

            return rawProductRegistry;

        } catch (Exception e) {
            logger.info(String.format("error while creating %s: ", RawProductRegistry.class.getSimpleName()), e);
            throw new ServiceException(String.format("error while creating %s: ", RawProductRegistry.class.getSimpleName()), e);
        }
    }
}
