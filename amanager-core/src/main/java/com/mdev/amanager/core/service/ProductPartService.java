package com.mdev.amanager.core.service;

import com.mdev.amanager.core.datasource.ProductPartDataSource;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.core.util.ServiceMessages;
import com.mdev.amanager.persistence.domain.model.Product;
import com.mdev.amanager.persistence.domain.model.ProductPart;
import com.mdev.amanager.persistence.domain.repository.ProductPartRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gmilazzo on 06/11/2018.
 */
@Service
public class ProductPartService {

    private static final Logger logger = LogManager.getLogger(ProductPartService.class);

    @Autowired
    private ProductPartRepository productPartRepository;

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public ProductPart create(ProductPartDataSource productPartDataSource) throws ServiceException {

        try {
            ProductPart pp = productPartDataSource.validate();
            productPartRepository.save(pp);
            logger.info(ServiceMessages.createSuccess(pp));
            return pp;
        } catch (Exception e) {
            logger.error(ServiceMessages.errorOnCreate(ProductPart.class), e);
            throw new ServiceException(ServiceMessages.errorOnCreate(ProductPart.class), e);
        }
    }

    @Transactional(readOnly = true)
    public List<ProductPart> findByProduct(Product product) {
        return productPartRepository.findByProduct(product);
    }
}
