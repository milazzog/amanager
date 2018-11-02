package com.mdev.amanager.core.service;

import com.mdev.amanager.core.datasource.RawProductDataSource;
import com.mdev.amanager.core.datasource.SubscriberCardDataSource;
import com.mdev.amanager.core.datasource.SubscriberDataSource;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.enums.UM;
import com.mdev.amanager.persistence.domain.model.RawProduct;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.repository.RawProductRepository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityPersistenceException;
import com.mdev.amanager.persistence.domain.repository.params.RawProductSearchParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<RawProduct> find(RawProductSearchParam param) {
        return rawProductRepository.findBySearchParam(param);
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public RawProduct create(RawProductDataSource rawProductDataSource) throws ServiceException {

        try {
            rawProductDataSource.setAddedAt(new Date());
            RawProduct rp = rawProductDataSource.validate();
            rp.setName(StringUtils.upperCase(rp.getName()));
            rawProductRepository.save(rp);
            return rp;
        } catch (Exception e) {
            logger.error(String.format("error occurred while creating %s:", RawProduct.class.getSimpleName()), e);
            throw new ServiceException(String.format("error occurred while creating %s:", RawProduct.class.getSimpleName()), e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public RawProduct edit(RawProduct rawProduct, RawProductDataSource rawProductDataSource) throws ServiceException {

        return edit(rawProduct.getId(), rawProductDataSource);
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public RawProduct edit(Long rawProductId, RawProductDataSource rawProductDataSource) throws ServiceException {

        if (Objects.isNull(rawProductId)) {
            throw new ServiceException(ServiceException.DETACHED_INSTANCE);
        }

        if (Objects.isNull(rawProductDataSource)) {
            throw new ServiceException(ServiceException.nullParam("rawProductDataSource"));
        }

        try {
            rawProductDataSource.validate();
            RawProduct rp = rawProductRepository.find(rawProductId);
            rp.setName(StringUtils.upperCase(rawProductDataSource.getName()));
            rp.setType(rawProductDataSource.getType());
            rp.setUm(rawProductDataSource.getUm());
            return rawProductRepository.merge(rp);
        } catch (Exception e) {
            logger.error(String.format("error occurred while editing %s:", RawProduct.class.getSimpleName()), e);
            throw new ServiceException(String.format("error occurred while editing %s:", RawProduct.class.getSimpleName()), e);
        }
    }
}
