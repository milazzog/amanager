package com.mdev.amanager.core.datasource;

import com.mdev.amanager.core.datasource.validation.DataSource;
import com.mdev.amanager.core.datasource.validation.Required;
import com.mdev.amanager.persistence.domain.model.Price;
import com.mdev.amanager.persistence.domain.model.Product;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gmilazzo on 06/11/2018.
 */
public class PriceDataSource extends DataSource<Price> {

    @Required
    private BigDecimal amount;
    @Required
    private Product product;
    @Required
    private Date addedAt;

    @Override
    protected Price asData() {
        Price p = new Price();

        p.setAmount(amount);
        p.setProduct(product);
        p.setAddedAt(addedAt);

        return p;
    }

    @Override
    public void fromData(Price data) {

        this.setAmount(data.getAmount());
        this.setProduct(data.getProduct());
        this.setAddedAt(data.getAddedAt());
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }
}
