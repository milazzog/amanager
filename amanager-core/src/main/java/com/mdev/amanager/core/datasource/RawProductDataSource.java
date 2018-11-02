package com.mdev.amanager.core.datasource;

import com.mdev.amanager.core.datasource.validation.DataSource;
import com.mdev.amanager.core.datasource.validation.Required;
import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.enums.UM;
import com.mdev.amanager.persistence.domain.model.RawProduct;

import java.util.Date;

/**
 * Created by gmilazzo on 31/10/2018.
 */
public class RawProductDataSource extends DataSource<RawProduct> {

    @Required
    private String name;
    @Required
    private ProductType type;
    @Required
    private UM um;
    @Required
    private Date addedAt;

    @Override
    protected RawProduct asData() {
        RawProduct rp = new RawProduct();
        rp.setName(getName());
        rp.setType(getType());
        rp.setUm(getUm());
        rp.setAddedAt(getAddedAt());
        return rp;
    }

    @Override
    public void fromData(RawProduct data) {

        this.setName(data.getName());
        this.setType(data.getType());
        this.setUm(data.getUm());
        this.setAddedAt(data.getAddedAt());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public UM getUm() {
        return um;
    }

    public void setUm(UM um) {
        this.um = um;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }
}
