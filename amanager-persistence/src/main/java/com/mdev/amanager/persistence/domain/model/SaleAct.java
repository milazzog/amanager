package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "SALE_ACT")
public class SaleAct implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "PRODUCT", foreignKey = @ForeignKey(name = "FK_SALE_ACT_PRODUCT"))
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "INCOME_NOTE", foreignKey = @ForeignKey(name = "FK_SALE_ACT_INCOME_NOTE"))
    private IncomeNote incomeNote;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDED_AT", nullable = false)
    private Date addedtAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "saleAct")
    private Set<WithdrawalOrder> withdrawalOrder;

    @Override
    public Long getId() {
        return id;
    }
}
