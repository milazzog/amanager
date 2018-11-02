package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "INCOME_NOTE")
public class IncomeNote implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "incomeNote")
    private Set<SaleAct> saleActs;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREDIT_NOTE", foreignKey = @ForeignKey(name = "FK_INCOME_NOTE_CREDIT_NOTE"))
    private CreditNote creditNote;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OPENED_AT", nullable = false)
    private Date openedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CLOSED_AT", nullable = false)
    private Date closedAt;

    @Override
    public Long getId() {
        return id;
    }

    public Set<SaleAct> getSaleActs() {
        return saleActs;
    }

    public void setSaleActs(Set<SaleAct> saleActs) {
        this.saleActs = saleActs;
    }

    public CreditNote getCreditNote() {
        return creditNote;
    }

    public void setCreditNote(CreditNote creditNote) {
        this.creditNote = creditNote;
    }

    public Date getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(Date openedAt) {
        this.openedAt = openedAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }
}
