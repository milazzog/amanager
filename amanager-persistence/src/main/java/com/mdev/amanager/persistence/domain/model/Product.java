package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "PRODUCT")
public class Product implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 32, nullable = false)
    private ProductType type;

    @Column(name = "NAME", length = 64, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private Set<ProductPart> productParts;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private Set<Price> prices;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDED_AT", nullable = false)
    private Date addedAt;

    @Column(name = "REMOVED_AT")
    private Date removedAt;

    @Override
    public Long getId() {
        return id;
    }
}
