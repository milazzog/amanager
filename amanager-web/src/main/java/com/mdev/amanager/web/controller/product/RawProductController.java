package com.mdev.amanager.web.controller.product;

import com.mdev.amanager.core.datasource.RawProductDataSource;
import com.mdev.amanager.core.service.RawProductService;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.enums.UM;
import com.mdev.amanager.persistence.domain.model.RawProduct;
import com.mdev.amanager.persistence.domain.repository.params.RawProductSearchParam;
import com.mdev.amanager.web.controller.base.RootManagedBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by gmilazzo on 31/10/2018.
 */
@ManagedBean
@ViewScoped
public class RawProductController extends RootManagedBean {

    private static final Logger logger = LogManager.getLogger(RawProductController.class);

    private RawProduct rawProduct;
    private RawProductSearchParam searchParam;
    private List<RawProduct> searchResults;
    private RawProductDataSource rawProductDataSource;

    @Autowired
    private RawProductService rawProductService;

    @PostConstruct
    public void init() {
        reset();
        beforeAdd();
        search();
    }

    public void search() {

        searchResults = rawProductService.find(searchParam);

    }

    public void reset() {

        searchParam = new RawProductSearchParam();
    }

    public void beforeAdd() {
        rawProductDataSource = new RawProductDataSource();
    }

    public void add() {

        try {

            RawProduct product = rawProductService.create(rawProductDataSource);
            beforeAdd();
            search();
            messageManager.info(null, "msg.info.raw.product.add", product.getName(), resourceBundle.getString(product.getType().getLabelKey()), product.getIdString());
        } catch (ServiceException e) {
            logger.error(String.format("error while adding %s:", RawProduct.class.getSimpleName()), e);
            if (e.getCause() instanceof DataIntegrityViolationException) {
                messageManager.error(null, "msg.error.raw.product.add.duplicate", rawProductDataSource.getName());
            } else {
                messageManager.error("msg.error.raw.product.add");
            }
        }
    }

    public void edit() {

        try {

            rawProduct = rawProductService.edit(rawProduct, rawProductDataSource);
            search();
            messageManager.info("msg.info.raw.product.edit");
        } catch (ServiceException e) {
            logger.error(String.format("error while adding %s:", RawProduct.class.getSimpleName()), e);
            if (e.getCause() instanceof DataIntegrityViolationException) {
                messageManager.error(null, "msg.error.raw.product.edit.duplicate", rawProductDataSource.getName());
            } else {
                messageManager.error("msg.error.raw.product.edit");
            }
        }
    }

    public void setSelectedProduct(RawProduct rawProduct) {

        rawProductDataSource.fromData(rawProduct);
        this.rawProduct = rawProduct;

    }

    public ProductType[] getProductTypes() {
        return ProductType.values();
    }

    public UM[] getUM() {
        return UM.values();
    }

    public RawProductSearchParam getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(RawProductSearchParam searchParam) {
        this.searchParam = searchParam;
    }

    public List<RawProduct> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<RawProduct> searchResults) {
        this.searchResults = searchResults;
    }

    public RawProductDataSource getRawProductDataSource() {
        return rawProductDataSource;
    }

    public void setRawProductDataSource(RawProductDataSource rawProductDataSource) {
        this.rawProductDataSource = rawProductDataSource;
    }
}
