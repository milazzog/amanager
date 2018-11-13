package com.mdev.amanager.core.service;

import com.mdev.amanager.core.datasource.PriceDataSource;
import com.mdev.amanager.core.util.ServiceMessages;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.persistence.domain.model.Price;
import com.mdev.amanager.persistence.domain.model.Product;
import com.mdev.amanager.persistence.domain.repository.PriceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by gmilazzo on 06/11/2018.
 */
@Service
public class PriceService {

    private static final Logger logger = LogManager.getLogger(PriceService.class);

    @Autowired
    private PriceRepository priceRepository;

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Price create(PriceDataSource dataSource) throws ServiceException {

        try {

            dataSource.setAddedAt(new Date());
            Price p = dataSource.validate();
            priceRepository.save(p);
            logger.info(ServiceMessages.createSuccess(p));
            return p;

        } catch (Exception e) {
            logger.error(ServiceMessages.errorOnCreate(Price.class), e);
            throw new ServiceException(ServiceMessages.errorOnCreate(Price.class), e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Price edit(Price price, PriceDataSource dataSource) throws ServiceException {

        if (Objects.isNull(price)) {
            throw new ServiceException(ServiceException.nullParam("price"));
        }

        if (Objects.isNull(dataSource)) {
            throw new ServiceException(ServiceException.nullParam("dataSource"));
        }

        try {

            if (price.getAmount().subtract(dataSource.getAmount()).compareTo(BigDecimal.ZERO) != 0) {
                logger.info(ServiceMessages.changesDetectedOnReadOnlyEntity(price));
                price.setRemovedAt(new Date());
                price = priceRepository.merge(price);
                dataSource.setProduct(price.getProduct());
                return create(dataSource);
            } else {
                logger.info(ServiceMessages.noChangesDetectedOnReadOnlyEntity(price));
                return price;
            }

        } catch (Exception e) {
            logger.error(ServiceMessages.errorOnCreate(Price.class), e);
            throw new ServiceException(ServiceMessages.errorOnCreate(Price.class), e);
        }
    }

    @Transactional(readOnly = true)
    public List<Price> findByProduct(Product product) {
        return priceRepository.findByProduct(product);
    }

}
