package com.mdev.amanager.web.controller.product;

import com.mdev.amanager.core.service.ExpenseNoteService;
import com.mdev.amanager.core.service.RawProductService;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.model.ExpenseNote;
import com.mdev.amanager.persistence.domain.model.RawProduct;
import com.mdev.amanager.persistence.domain.model.RawProductRegistry;
import com.mdev.amanager.web.controller.base.RootManagedBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by gmilazzo on 02/11/2018.
 */
@ManagedBean
@ViewScoped
public class RawProductReloadController extends RootManagedBean {

    private static final Logger logger = LogManager.getLogger(RawProductReloadController.class);

    private List<RawProductRegistry> rawProductRegistries;
    private RawProductRegistry rawProductRegistry;
    private ProductType productType;

    @Autowired
    private RawProductService rawProductService;
    @Autowired
    private ExpenseNoteService expenseNoteService;

    @PostConstruct
    public void init() {
        clear();
    }

    public List<RawProduct> autocompleteProduct(String part) {

        return Objects.isNull(productType) ? rawProductService.find(part) : rawProductService.find(part, productType);
    }

    public void add() {

        rawProductRegistries.add(rawProductRegistry);
        rawProductRegistry = new RawProductRegistry();
    }

    public void remove(RawProductRegistry rawProductRegistry) {
        rawProductRegistries.remove(rawProductRegistry);
    }

    public void clear() {
        rawProductRegistries = new ArrayList<>();
        rawProductRegistry = new RawProductRegistry();
        productType = null;
    }

    public void save() {

        try {

            expenseNoteService.create(rawProductRegistries);
            messageManager.info("msg.info.raw.product.reload");
            clear();
        } catch (ServiceException e) {
            logger.error(String.format("error while creating %s: ", ExpenseNote.class.getSimpleName()), e);
            messageManager.error("msg.error.raw.product.reload");
        }
    }

    public void updateAutocomplete() {
        if (Objects.nonNull(productType) && Objects.nonNull(rawProductRegistry.getRawProduct()) && productType != rawProductRegistry.getRawProduct().getType()) {
            rawProductRegistry.setRawProduct(null);
        }
    }

    public void updateSelector() {

        if (Objects.nonNull(rawProductRegistry.getRawProduct())) {
            productType = rawProductRegistry.getRawProduct().getType();
        }
    }

    public ProductType[] getProductTypes() {
        return ProductType.values();
    }

    public RawProductRegistry getRawProductRegistry() {
        return rawProductRegistry;
    }

    public void setRawProductRegistry(RawProductRegistry rawProductRegistry) {
        this.rawProductRegistry = rawProductRegistry;
    }

    public List<RawProductRegistry> getRawProductRegistries() {
        return rawProductRegistries;
    }

    public void setRawProductRegistries(List<RawProductRegistry> rawProductRegistries) {
        this.rawProductRegistries = rawProductRegistries;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
