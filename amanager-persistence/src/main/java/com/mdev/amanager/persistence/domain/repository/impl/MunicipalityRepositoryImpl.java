package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.model.User;
import com.mdev.amanager.persistence.domain.repository.MunicipalityRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

/**
 * Created by gmilazzo on 02/10/2018.
 */
@Repository
@Transactional
public class MunicipalityRepositoryImpl extends BaseRepository<Municipality> implements MunicipalityRepository{

    @Override
    public Class<Municipality> getManagedClass() {
        return Municipality.class;
    }

    @Override
    public List<Municipality> findAllProvinces() {

        return this
                .named("municipality.find.all.provinces")
                .getResultList();
    }

    @Override
    public List<Municipality> findAllMunicipalies() {

        return this
                .named("municipality.find.all.municipalies")
                .getResultList();
    }

    @Override
    public Municipality findByBelfioreCode(String belfioreCode) {

        return this
                .named("municipality.find.by.belfiore.code")
                .setParameter("belfioreCode", belfioreCode)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<Municipality> findByName(String name) {

        return this.named("municipality.find.by.name")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Municipality> findByNamePattern(String namePattern) {

        return this
                .named("municipality.find.by.name.pattern")
                .setParameter("name", like(namePattern))
                .getResultList();
    }

    @Override
    public List<Municipality> findByProvince(String province) {

        return this
                .named("municipality.find.by.province")
                .setParameter("province", province)
                .getResultList();
    }

    @Override
    public List<Municipality> findByZip(String zip) {

        return this
                .named("municipality.find.by.zip")
                .setParameter("zip", zip)
                .getResultList();
    }

    @Override
    public List<Municipality> findByZipPattern(String zipPattern) {

        return this
                .named("municipality.find.by.zip.pattern")
                .setParameter("zip", like(zipPattern))
                .getResultList();
    }
}
