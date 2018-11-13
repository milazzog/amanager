package com.mdev.amanager.web.controller.product;

import com.mdev.amanager.core.datasource.PriceDataSource;
import com.mdev.amanager.core.datasource.ProductDataSource;
import com.mdev.amanager.core.datasource.ProductPartDataSource;
import com.mdev.amanager.core.service.ProductService;
import com.mdev.amanager.core.service.RawProductService;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.enums.UM;
import com.mdev.amanager.persistence.domain.model.Price;
import com.mdev.amanager.persistence.domain.model.Product;
import com.mdev.amanager.persistence.domain.model.RawProduct;
import com.mdev.amanager.persistence.domain.repository.params.ProductSearchParam;
import com.mdev.amanager.web.controller.base.RootManagedBean;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gmilazzo on 31/10/2018.
 */
@ManagedBean
@ViewScoped
public class ProductController extends RootManagedBean {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    private ProductDataSource productDataSource;
    private ProductPartDataSource productPartDataSource;
    private List<ProductPartDataSource> productPartDataSources;
    private PriceDataSource priceDataSource;
    private ProductSearchParam searchParam;
    private List<Product> searchResults;
    private Product product;

    @Autowired
    private ProductService productService;

    @Autowired
    private RawProductService rawProductService;


    @PostConstruct
    public void init() {
        reset();
        beforeAdd();
        search();
    }

    public Price getActivePrice(Product product) {

        return productService.getActivePrice(product);
    }

    public Integer getAvailableQuantity(Product product) {
        return productService.getAvailableQuantity(product);
    }

    public void search() {

        searchResults = productService.findBySearchParam(searchParam);

    }

    public void reset() {

        searchParam = new ProductSearchParam();
    }

    public void beforeAdd() {

        productDataSource = new ProductDataSource();
        productPartDataSources = new ArrayList<>();
        priceDataSource = new PriceDataSource();
        productPartDataSource = new ProductPartDataSource();
    }

    public void addPart() {
        productPartDataSources.add(productPartDataSource);
        resetPart();
    }

    public void resetPart() {
        productPartDataSource = new ProductPartDataSource();
    }

    public void removePart(ProductPartDataSource dataSource) {
        productPartDataSources.remove(dataSource);
    }

    public void add() {

        try {

            if (CollectionUtils.isEmpty(productPartDataSources)) {
                messageManager.error("validation.product.product.part.mandatory");
                return;
            }

            Product product = productService.create(productDataSource, productPartDataSources, priceDataSource);
            beforeAdd();
            search();
            messageManager.info(null, "msg.info.product.add", product.getName(), resourceBundle.getString(product.getType().getLabelKey()), product.getIdString());
        } catch (ServiceException e) {
            logger.error(String.format("error while adding %s:", Product.class.getSimpleName()), e);
            messageManager.error("msg.error.product.add");
        }
    }

    public void edit() {

        try {

            if (CollectionUtils.isEmpty(productPartDataSources)) {
                messageManager.error("validation.product.product.part.mandatory");
                return;
            }

            product = productService.edit(product, productDataSource, productPartDataSources, priceDataSource);
            search();
            messageManager.info("msg.info.product.edit");
        } catch (ServiceException e) {
            logger.error(String.format("error while editing %s:", Product.class.getSimpleName()), e);
            messageManager.error("msg.error.product.edit");
        }
    }

    public List<RawProduct> autocompleteRawProduct(String part) {

        return rawProductService.find(part);
    }

    public void setSelectedProduct(Product product) {

        this.product = product;
        this.productDataSource.fromData(product);
        this.productPartDataSources = product
                .getProductPartRegistryRegistry()
                .getParts()
                .stream()
                .map(p -> {
                    ProductPartDataSource d = new ProductPartDataSource();
                    d.fromData(p);
                    return d;
                })
                .collect(Collectors.toList());
        this.priceDataSource = new PriceDataSource();
        this.priceDataSource.fromData(productService.getActivePrice(this.product));
    }

    public ProductType[] getProductTypes() {
        return ProductType.values();
    }

    public ProductSearchParam getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(ProductSearchParam searchParam) {
        this.searchParam = searchParam;
    }

    public List<Product> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Product> searchResults) {
        this.searchResults = searchResults;
    }

    public ProductDataSource getProductDataSource() {
        return productDataSource;
    }

    public void setProductDataSource(ProductDataSource productDataSource) {
        this.productDataSource = productDataSource;
    }

    public List<ProductPartDataSource> getProductPartDataSources() {
        return productPartDataSources;
    }

    public void setProductPartDataSources(List<ProductPartDataSource> productPartDataSources) {
        this.productPartDataSources = productPartDataSources;
    }

    public PriceDataSource getPriceDataSource() {
        return priceDataSource;
    }

    public void setPriceDataSource(PriceDataSource priceDataSource) {
        this.priceDataSource = priceDataSource;
    }

    public ProductPartDataSource getProductPartDataSource() {
        return productPartDataSource;
    }

    public void setProductPartDataSource(ProductPartDataSource productPartDataSource) {
        this.productPartDataSource = productPartDataSource;
    }
}
