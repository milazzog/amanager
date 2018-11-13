package com.mdev.amanager.core.service;

import com.mdev.amanager.core.datasource.PriceDataSource;
import com.mdev.amanager.core.datasource.ProductDataSource;
import com.mdev.amanager.core.datasource.ProductPartDataSource;
import com.mdev.amanager.core.datasource.validation.MissingRequiredFieldException;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.core.util.ServiceMessages;
import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.model.*;
import com.mdev.amanager.persistence.domain.repository.ProductRepository;
import com.mdev.amanager.persistence.domain.repository.params.ProductSearchParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by gmilazzo on 03/11/2018.
 */
@Service
public class ProductService {

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPartRegistryService productPartRegistryService;

    @Autowired
    private PriceService priceService;

    public Integer getAvailableQuantity(Product product) {

        if (Objects.nonNull(product) && Objects.nonNull(product.getProductPartRegistryRegistry()) && CollectionUtils.isNotEmpty(product.getProductPartRegistryRegistry().getParts())) {

            return product.getProductPartRegistryRegistry().getParts()
                    .stream()
                    .map(p -> p.getRawProduct()
                            .getRawProductRegistries()
                            .stream()
                            .filter(r -> !r.isOver())
                            .map(r -> r.getQuantity()
                                    .subtract(r.getWithdrawalOrders()
                                            .stream()
                                            .map(WithdrawalOrder::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add)))
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .divide(p.getQuantity(), BigDecimal.ROUND_HALF_DOWN)
                    ).min(Comparator.naturalOrder()).orElse(BigDecimal.ZERO).intValue();
        }
        return 0;
    }

    public Price getActivePrice(Product product) {

        if (Objects.nonNull(product) && CollectionUtils.isNotEmpty(product.getPrices())) {

            return product.getPrices().stream().filter(p -> Objects.isNull(p.getRemovedAt())).findFirst().orElse(null);
        }

        return null;
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Product create(ProductDataSource productDataSource, Collection<ProductPartDataSource> productPartDataSources, PriceDataSource priceDataSource) throws ServiceException {

        try {

            productDataSource.setAddedAt(new Date());
            Product product = productDataSource.validate();

            productRepository.save(product);


            ProductPartRegistry productPartRegistry = productPartRegistryService.create(product, productPartDataSources);

            product.setProductPartRegistryRegistry(productPartRegistry);
            product.getProductPartRegistryRegistries().add(productPartRegistry);

            priceDataSource.setProduct(product);
            Price price = priceService.create(priceDataSource);
            product.getPrices().add(price);

            product = productRepository.merge(product);
            logger.info(ServiceMessages.createSuccess(product));

            return product;

        } catch (Exception e) {
            logger.error(ServiceMessages.errorOnCreate(Product.class), e);
            throw new ServiceException(ServiceMessages.errorOnCreate(Product.class), e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Product edit(Product product, ProductDataSource productDataSource, Collection<ProductPartDataSource> productPartDataSources, PriceDataSource priceDataSource) throws ServiceException {

        if (Objects.isNull(product)) {
            throw new ServiceException(ServiceException.nullParam("product"));
        }

        if (Objects.isNull(productDataSource)) {
            throw new ServiceException(ServiceException.nullParam("productDataSource"));
        }

        try {

            productDataSource.validate();
            priceDataSource.validate();
            for (ProductPartDataSource ppds : productPartDataSources) {
                ppds.validate();
            }

            Product p = productRepository.find(product.getId());

            ProductPartRegistry oldPartRegistry = product.getProductPartRegistryRegistry();
            ProductPartRegistry newPartRegistry = productPartRegistryService.edit(product.getProductPartRegistryRegistry(), productPartDataSources);

            if (!oldPartRegistry.equals(newPartRegistry)) {
                product.setProductPartRegistryRegistry(newPartRegistry);
                product.getProductPartRegistryRegistries().add(newPartRegistry);
            }

            Price oldPrice = product.getPrices().stream().filter(price -> Objects.isNull(price.getRemovedAt())).findFirst().orElseThrow(() -> new ServiceException(String.format("no active price found for %s with id %s.", Product.class.getSimpleName(), product.getIdString())));
            Price newPrice = priceService.edit(oldPrice, priceDataSource);

            if (!oldPrice.equals(newPrice)) {
                product.getPrices().add(newPrice);
            }

            if (!product.getType().equals(productDataSource.getType())) {
                product.setType(productDataSource.getType());
            }

            if (!StringUtils.equalsIgnoreCase(product.getName(), productDataSource.getName())) {
                product.setName(productDataSource.getName());
            }

            Product modified = productRepository.merge(product);

            logger.info(ServiceMessages.editSuccess(modified));

            return modified;

        } catch (Exception e) {
            logger.error(ServiceMessages.errorOnEdit(product), e);
            throw new ServiceException(ServiceMessages.errorOnEdit(product), e);
        }
    }

    @Transactional(readOnly = true)
    public List<Product> findBySearchParam(ProductSearchParam searchParam) {
        return productRepository.findBySearchParams(searchParam);
    }
}
