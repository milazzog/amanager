package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "WITHDRAWAL_ORDER")
public class WithdrawalOrder implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "RAW_PRODUCT_REGISTRY", foreignKey = @ForeignKey(name = "FK_WITHDRAWAL_ORDER_RAW_PRODUCT_REGISTRY"))
    private RawProductRegistry rawProductRegistry;

    @Column(name = "QUANTITY", precision = 8, scale = 2, nullable = false)
    private BigDecimal quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SALE_ACT", foreignKey = @ForeignKey(name = "FK_WITHDRAWAL_ORDER_SALE_ACT"))
    private SaleAct saleAct;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @Override
    public Long getId() {
        return id;
    }

    public RawProductRegistry getRawProductRegistry() {
        return rawProductRegistry;
    }

    public void setRawProductRegistry(RawProductRegistry rawProductRegistry) {
        this.rawProductRegistry = rawProductRegistry;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public SaleAct getSaleAct() {
        return saleAct;
    }

    public void setSaleAct(SaleAct saleAct) {
        this.saleAct = saleAct;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
