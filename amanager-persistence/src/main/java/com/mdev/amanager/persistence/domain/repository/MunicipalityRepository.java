package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.repository.base.Repository;

import java.util.List;

/**
 * Created by gmilazzo on 02/10/2018.
 */
public interface MunicipalityRepository extends Repository<Municipality> {

    List<Municipality> findAllProvinces();

    List<Municipality> findAllMunicipalies();

    Municipality findByBelfioreCode(String belfioreCode);

    List<Municipality> findByName(String name);

    List<Municipality> findByNamePattern(String namePattern);

    List<Municipality> findByProvince(String province);

    List<Municipality> findByZip(String zip);

    List<Municipality> findByZipPattern(String zipPattern);
}
