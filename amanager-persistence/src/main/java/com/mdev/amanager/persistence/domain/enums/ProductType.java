package com.mdev.amanager.persistence.domain.enums;

import com.mdev.amanager.persistence.domain.model.base.Labeled;

/**
 * Created by gmilazzo on 25/10/2018.
 */
public enum ProductType implements Labeled {

    FOOD_AND_BEVERAGES("product.type.food.and.beverages"),
    BUILDING("product.type.building"),
    CHANCELLERY("product.type.chancellery"),
    OTHER("product.type.other");

    private String labelKey;

    ProductType(String labelKey) {
        this.labelKey = labelKey;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }
}
