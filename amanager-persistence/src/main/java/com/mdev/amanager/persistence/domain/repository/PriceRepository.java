package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.model.Price;
import com.mdev.amanager.persistence.domain.model.Product;
import com.mdev.amanager.persistence.domain.repository.base.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by gmilazzo on 06/11/2018.
 */
public interface PriceRepository extends Repository<Price> {

    List<Price> findByProduct(Product product);
}
