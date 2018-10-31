package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "PRICE", uniqueConstraints = @UniqueConstraint(name = "PRICE_UNIQUE", columnNames = {"PRODUCT", "ADDED_AT"}))
public class Price implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "PRODUCT", foreignKey = @ForeignKey(name = "FK_PRICE_PRODUCT"))
    private Product product;

    @Column(name = "AMOUNT", precision = 8, scale = 2, nullable = false)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDED_AT", nullable = false)
    private Date addedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REMOVED_AT")
    private Date removedAt;

    @Override
    public Long getId() {
        return id;
    }
}
