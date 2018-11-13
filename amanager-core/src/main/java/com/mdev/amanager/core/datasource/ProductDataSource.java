package com.mdev.amanager.core.datasource;

import com.mdev.amanager.core.datasource.validation.DataSource;
import com.mdev.amanager.core.datasource.validation.Required;
import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.model.Product;

import java.util.Date;

/**
 * Created by gmilazzo on 06/11/2018.
 */
public class ProductDataSource extends DataSource<Product> {

    @Required
    private ProductType type;
    @Required
    private String name;
    @Required
    private Date addedAt;

    @Override
    protected Product asData() {

        Product p = new Product();

        p.setType(type);
        p.setName(name);
        p.setAddedAt(addedAt);

        return p;
    }

    @Override
    public void fromData(Product data) {

        this.setType(data.getType());
        this.setName(data.getName());
        this.setAddedAt(data.getAddedAt());
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }
}
