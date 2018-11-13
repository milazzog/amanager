package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "EXPENSE_NOTE")
public class ExpenseNote implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREDIT_NOTE", foreignKey = @ForeignKey(name = "FK_EXPENSE_NOTE_CREDIT_NOTE"))
    private CreditNote creditNote;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "expenseNote")
    private List<RawProductRegistry> rawProductRegistries = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    public CreditNote getCreditNote() {
        return creditNote;
    }

    public void setCreditNote(CreditNote creditNote) {
        this.creditNote = creditNote;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<RawProductRegistry> getRawProductRegistries() {
        return rawProductRegistries;
    }

    public void setRawProductRegistries(List<RawProductRegistry> rawProductRegistries) {
        this.rawProductRegistries = rawProductRegistries;
    }
}
