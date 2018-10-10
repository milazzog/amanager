package com.mdev.amanager.persistence.domain.model.base;

import com.mdev.amanager.persistence.domain.model.Municipality;

/**
 * Created by gmilazzo on 08/10/2018.
 */
public interface Localizable {

    Municipality getCity();
    String getAddress();
}
