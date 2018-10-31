package com.mdev.amanager.core.service;

import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.enums.UM;
import com.mdev.amanager.persistence.domain.model.RawProduct;
import com.mdev.amanager.persistence.domain.repository.RawProductRepository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityPersistenceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Service
public class RawProductService {

    private static final Logger logger = LogManager.getLogger(RawProductService.class);

    @Autowired
    private RawProductRepository rawProductRepository;

    public List<RawProduct> find(String name) {
        return rawProductRepository.findByNamePattern(name);
    }

    public List<RawProduct> find(String name, ProductType type) {
        return rawProductRepository.findByNamePatternAndType(name, type);
    }

    public RawProduct getOrAdd(String name, ProductType type, UM um) throws ServiceException {

        if (StringUtils.isBlank(name)) {
            throw new ServiceException(ServiceException.blankString("name"));
        }

        if (Objects.isNull(type)) {
            throw new ServiceException(ServiceException.nullParam("type"));
        }

        if (Objects.isNull(um)) {
            throw new ServiceException(ServiceException.nullParam("um"));
        }

        RawProduct product;
        try {
            logger.info(String.format("searching for %s with name %s", RawProduct.class.getSimpleName(), name));
            product = rawProductRepository.findByName(name);
            logger.info(String.format("found %s with name %s and id [%s]", RawProduct.class.getSimpleName(), product.getName(), product.getIdString()));
        } catch (EntityPersistenceException e) {
            logger.info(String.format("no %s found with name %s, adding new...", RawProduct.class.getSimpleName(), name));
            product = new RawProduct();

            product.setAddedAt(new Date());
            product.setName(name);
            product.setType(type);
            product.setUm(um);

            rawProductRepository.save(product);
            logger.info(String.format("added %s with name %s and id [%s]", RawProduct.class.getSimpleName(), product.getName(), product.getIdString()));
        }
        return product;
    }
}
