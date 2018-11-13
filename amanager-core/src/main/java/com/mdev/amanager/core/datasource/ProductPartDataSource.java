package com.mdev.amanager.core.datasource;

import com.mdev.amanager.core.datasource.validation.DataSource;
import com.mdev.amanager.core.datasource.validation.Required;
import com.mdev.amanager.persistence.domain.model.Product;
import com.mdev.amanager.persistence.domain.model.ProductPart;
import com.mdev.amanager.persistence.domain.model.ProductPartRegistry;
import com.mdev.amanager.persistence.domain.model.RawProduct;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by gmilazzo on 06/11/2018.
 */
public class ProductPartDataSource extends DataSource<ProductPart> {

    @Required
    private RawProduct rawProduct;
    @Required
    private BigDecimal quantity;
    @Required
    private ProductPartRegistry productPartRegistry;

    @Override
    protected ProductPart asData() {
        ProductPart pp = new ProductPart();

        pp.setRawProduct(rawProduct);
        pp.setQuantity(quantity);
        pp.setProductPartRegistry(productPartRegistry);

        return pp;
    }


    @Override
    public void fromData(ProductPart data) {

        this.setRawProduct(data.getRawProduct());
        this.setQuantity(data.getQuantity());
        this.setProductPartRegistry(data.getProductPartRegistry());
    }

    public RawProduct getRawProduct() {
        return rawProduct;
    }

    public void setRawProduct(RawProduct rawProduct) {
        this.rawProduct = rawProduct;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public ProductPartRegistry getProductPartRegistry() {
        return productPartRegistry;
    }

    public void setProductPartRegistry(ProductPartRegistry productPartRegistry) {
        this.productPartRegistry = productPartRegistry;
    }
}
