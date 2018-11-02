package com.mdev.amanager.persistence.domain.repository.params;

import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.enums.UM;
import com.mdev.amanager.persistence.domain.repository.params.base.SearchParam;
import com.mdev.amanager.persistence.domain.repository.params.base.StringMatcher;

/**
 * Created by gmilazzo on 31/10/2018.
 */
public class RawProductSearchParam extends SearchParam {

    private StringMatcher name;
    private ProductType type;
    private UM um;

    public StringMatcher getName() {
        return name;
    }

    public void setName(StringMatcher name) {
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
}
