package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.enums.CreditCausal;
import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gmilazzo on 21/10/2018.
 */
@Entity
@Table(name = "CREDIT_NOTE")
public class CreditNote implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDED_AT", nullable = false)
    private Date addedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "CAUSAL", length = 32, nullable = false)
    private CreditCausal causal;

    @Column(name = "AMOUNT", precision = 8, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "NOTES", length = 256)
    private String notes;

    @Override
    public Long getId() {
        return id;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public CreditCausal getCausal() {
        return causal;
    }

    public void setCausal(CreditCausal causal) {
        this.causal = causal;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditNote)) return false;

        CreditNote that = (CreditNote) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (addedAt != null ? !addedAt.equals(that.addedAt) : that.addedAt != null) return false;
        if (causal != that.causal) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return notes != null ? notes.equals(that.notes) : that.notes == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (addedAt != null ? addedAt.hashCode() : 0);
        result = 31 * result + (causal != null ? causal.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
