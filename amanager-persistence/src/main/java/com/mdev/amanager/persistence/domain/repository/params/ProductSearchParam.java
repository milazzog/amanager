package com.mdev.amanager.persistence.domain.repository.params;

import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.repository.params.base.BigDecimalMatcher;
import com.mdev.amanager.persistence.domain.repository.params.base.NumberMatcher;
import com.mdev.amanager.persistence.domain.repository.params.base.SearchParam;
import com.mdev.amanager.persistence.domain.repository.params.base.StringMatcher;

import java.math.BigDecimal;

/**
 * Created by gmilazzo on 02/11/2018.
 */
public class ProductSearchParam extends SearchParam {

    private ProductType type;
    private StringMatcher name;
    private BigDecimalMatcher price;

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public StringMatcher getName() {
        return name;
    }

    public void setName(StringMatcher name) {
        this.name = name;
    }

    public BigDecimalMatcher getPrice() {
        return price;
    }

    public void setPrice(BigDecimalMatcher price) {
        this.price = price;
    }
}
