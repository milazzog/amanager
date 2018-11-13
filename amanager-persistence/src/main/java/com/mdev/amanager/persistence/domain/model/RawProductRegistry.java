package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "RAW_PRODUCT_REGISTRY")
public class RawProductRegistry implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "EXPENSE_NOTE", foreignKey = @ForeignKey(name = "FK_RAW_PRODUCT_REGISTRY_EXPENSE_NOTE"))
    private ExpenseNote expenseNote;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "RAW_PRODUCT", foreignKey = @ForeignKey(name = "FK_RAW_PRODUCT_REGISTRY_RAW_PRODUCT"))
    private RawProduct rawProduct;

    @Column(name = "QUANTITY", precision = 8, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "AMOUNT", precision = 8, scale = 2, nullable = false)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDED_AT", nullable = false)
    private Date addedAt;

    @Column(name = "OVER", nullable = false)
    private boolean over;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OVER_AT")
    private Date overAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rawProductRegistry")
    private Set<WithdrawalOrder> withdrawalOrders;

    @Override
    public Long getId() {
        return id;
    }

    public ExpenseNote getExpenseNote() {
        return expenseNote;
    }

    public void setExpenseNote(ExpenseNote expenseNote) {
        this.expenseNote = expenseNote;
    }

    public RawProduct getRawProduct() {
        return rawProduct;
    }

    public void setRawProduct(RawProduct rawProduct) {
        this.rawProduct = rawProduct;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public Date getOverAt() {
        return overAt;
    }

    public void setOverAt(Date overAt) {
        this.overAt = overAt;
    }

    public Set<WithdrawalOrder> getWithdrawalOrders() {
        return withdrawalOrders;
    }

    public void setWithdrawalOrders(Set<WithdrawalOrder> withdrawalOrders) {
        this.withdrawalOrders = withdrawalOrders;
    }
}
