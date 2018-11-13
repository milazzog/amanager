package com.mdev.amanager.core.service;

import com.mdev.amanager.core.datasource.ProductPartDataSource;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.core.util.ServiceMessages;
import com.mdev.amanager.persistence.domain.model.Product;
import com.mdev.amanager.persistence.domain.model.ProductPart;
import com.mdev.amanager.persistence.domain.model.ProductPartRegistry;
import com.mdev.amanager.persistence.domain.model.RawProduct;
import com.mdev.amanager.persistence.domain.repository.ProductPartRegistryRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gmilazzo on 13/11/2018.
 */
@Service
public class ProductPartRegistryService {

    private static final Logger logger = LogManager.getLogger(ProductPartRegistryService.class);

    @Autowired
    private ProductPartRegistryRepository productPartRegistryRepository;

    @Autowired
    private ProductPartService productPartService;

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public ProductPartRegistry create(Product product, Collection<ProductPartDataSource> productPartDataSources) throws ServiceException {

        if (Objects.isNull(product)) {
            throw new ServiceException(ServiceException.nullParam("product"));
        }

        if (CollectionUtils.isEmpty(productPartDataSources)) {
            throw new ServiceException(ServiceException.emptyCollection("productPartDataSources"));
        }

        try {

            ProductPartRegistry productPartRegistry = new ProductPartRegistry();

            productPartRegistry.setProduct(product);
            productPartRegistry.setAddedAt(new Date());

            productPartRegistryRepository.save(productPartRegistry);

            for (ProductPartDataSource part : productPartDataSources) {
                part.setProductPartRegistry(productPartRegistry);
                ProductPart productPart = productPartService.create(part);
                productPartRegistry.getParts().add(productPart);
            }

            productPartRegistry = productPartRegistryRepository.merge(productPartRegistry);

            logger.info(ServiceMessages.createSuccess(productPartRegistry));

            return productPartRegistry;

        } catch (Exception e) {
            logger.error(ServiceMessages.errorOnCreate(ProductPartRegistry.class), e);
            throw new ServiceException(ServiceMessages.errorOnCreate(ProductPartRegistry.class), e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public ProductPartRegistry edit(ProductPartRegistry productPartRegistry, Collection<ProductPartDataSource> productPartDataSources) throws ServiceException {

        if (Objects.isNull(productPartRegistry)) {
            throw new ServiceException(ServiceException.nullParam("productPartRegistry"));
        }

        if (CollectionUtils.isEmpty(productPartDataSources)) {
            throw new ServiceException(ServiceException.emptyCollection("productPartDataSources"));
        }

        try {

            ProductPartRegistry ppr = productPartRegistryRepository.find(productPartRegistry.getId());
            Map<Long, BigDecimal> oldParts = ppr.getParts().stream().collect(Collectors.toMap(p -> p.getRawProduct().getId(), ProductPart::getQuantity));
            Map<Long, BigDecimal> newParts = productPartDataSources.stream().collect(Collectors.toMap(p -> p.getRawProduct().getId(), ProductPartDataSource::getQuantity));

            if (!comparePartMap(oldParts, newParts)) {
                logger.info(ServiceMessages.changesDetectedOnReadOnlyEntity(ppr));
                ppr.setRemovedAt(new Date());
                ppr = productPartRegistryRepository.merge(ppr);
                return create(ppr.getProduct(), productPartDataSources);
            } else {
                logger.info(ServiceMessages.noChangesDetectedOnReadOnlyEntity(ppr));
                return ppr;
            }

        } catch (Exception e) {
            logger.error(ServiceMessages.errorOnCreate(ProductPartRegistry.class), e);
            throw new ServiceException(ServiceMessages.errorOnCreate(ProductPartRegistry.class), e);
        }
    }

    private boolean comparePartMap(Map<Long, BigDecimal> oldParts, Map<Long, BigDecimal> newParts) {

        if (oldParts.size() != newParts.size()) {
            return false;
        }
        for (Long l1 : newParts.keySet()) {
            BigDecimal oldQuantity = oldParts.get(l1);
            if (Objects.isNull(oldQuantity) || !oldQuantity.equals(newParts.get(l1))) {
                return false;
            }
        }
        return true;
    }
}
